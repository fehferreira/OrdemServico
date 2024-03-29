package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoAtualizadoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.NenhumObjetoCadastradoException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ObjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObjetoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("Test")
class ObjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ObjetoService serviceMock;

    private List<Objeto> listaObjetos;

    private final String url = "/objetos";

    @BeforeEach
    private void beforeEach(){
        this.listaObjetos = new ArrayList<>();
        listaObjetos.add(new Objeto(1L, "Marelli", "IAW 1G7"));
        listaObjetos.add(new Objeto(3L, "Bosch", "ME744"));
        listaObjetos.add(new Objeto(8L, "Ford", "EEC-V"));
    }

    @Test
    void exibirAparelhos_deveriaRetornarListaObjetoDto() throws Exception {
        when(serviceMock.exibirObjetos()).thenReturn(listaObjetos);

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String listaObjetosRetornoJSON = mvcResult.getResponse().getContentAsString();
        String listaExperadaJSON = mapper.writeValueAsString(this.listaObjetos.stream().map(ObjetoDTO::new));

        assertThat(listaObjetosRetornoJSON).isEqualToIgnoringWhitespace(listaExperadaJSON);
    }

    @Test
    void exibirAparelhos_deveriaRetornarExcecao_DBVazio() throws Exception {
        when(serviceMock.exibirObjetos())
                .thenThrow(new NenhumObjetoCadastradoException("Nenhum objeto encontrado no Banco de Dados!"));

        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.localizedMessage",
                        is("Nenhum objeto encontrado no Banco de Dados!")));
    }

    @Test
    void cadastrarObjeto_deveriaRetornarObjetoCadastrado() throws Exception {
        Objeto objeto = this.listaObjetos.get(0);
        ObjetoCadastradoForm formCadastro = converterObjetoEmFormCadastro(objeto);

        when(serviceMock.cadastrarObjeto(any())).thenReturn(objeto);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marca", is(objeto.getMarca())))
                .andExpect(jsonPath("$.modelo", is(objeto.getModelo())));
    }

    @Test
    void cadastrarObjeto_deveriaRetornarException_ObjetoExistente() throws Exception{
        Objeto objeto = this.listaObjetos.get(2);
        ObjetoCadastradoForm formCadastro = converterObjetoEmFormCadastro(objeto);

        when(serviceMock.cadastrarObjeto(any()))
                .thenThrow(new ObjetoExistenteException("Objeto já existe no Banco de Dados."));

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.localizedMessage",
                        is("Objeto já existe no Banco de Dados.")));
    }

    @Test
    void cadastrarObjeto_deveriaRetornarException_ErroBanco() throws Exception{
        Objeto objeto = this.listaObjetos.get(2);
        ObjetoCadastradoForm formCadastro = converterObjetoEmFormCadastro(objeto);

        when(serviceMock.cadastrarObjeto(any()))
                .thenThrow(new IllegalArgumentException("Impossível salvar este usuário."));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.localizedMessage",
                        is("Impossível salvar este usuário.")));
    }

    @Test
    void deletarObjeto_deveriaRetornarObjetoDTODeletado() throws Exception {
        Objeto objeto = this.listaObjetos.get(0);

        when(serviceMock.deletarObjeto(objeto.getId())).thenReturn(objeto);

        mockMvc.perform(delete(url).param("id", objeto.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca", is(objeto.getMarca())))
                .andExpect(jsonPath("$.modelo", is(objeto.getModelo())));

        verify(serviceMock).deletarObjeto(objeto.getId());
    }

    @Test
    void deletarObjeto_deveriaRetornarException_NaoEncontrouObjeto() throws Exception {
        when(serviceMock.deletarObjeto(1L))
                .thenThrow(new ObjetoInexistenteException("Este objeto não existe no Banco de Dados."));

        mockMvc.perform(delete(url).param("id", String.valueOf(1L)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.localizedMessage",
                        is("Este objeto não existe no Banco de Dados.")));
    }

    @Test
    void alterarObjeto_deveriaRetornarObjetoAtualizado() throws Exception {
        Objeto objeto = this.listaObjetos.get(2);
        ObjetoAtualizadoForm formAtualizado =
                new ObjetoAtualizadoForm(objeto.getId(), objeto.getMarca(), objeto.getModelo());

        when(serviceMock.alterarObjeto(any())).thenReturn(objeto);

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formAtualizado)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marca", is(objeto.getMarca())))
                .andExpect(jsonPath("$.modelo", is(objeto.getModelo())));
    }

    @Test
    void alterarObjeto_deveriaRetornarExceptiono_ObjetoInexistente() throws Exception{
        Objeto objeto = this.listaObjetos.get(2);
        ObjetoAtualizadoForm formAtualizado =
                new ObjetoAtualizadoForm(objeto.getId(), objeto.getMarca(), objeto.getModelo());

        when(serviceMock.alterarObjeto(any()))
                .thenThrow(new ObjetoInexistenteException("Este objeto não existe no Banco de Dados."));

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formAtualizado)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.localizedMessage",
                            is("Este objeto não existe no Banco de Dados.")));
    }

    private ObjetoCadastradoForm converterObjetoEmFormCadastro(Objeto objeto) {
        return new ObjetoCadastradoForm(objeto.getMarca(), objeto.getModelo());
    }

}