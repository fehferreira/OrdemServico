package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.OrdemDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoAtualizadaForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import br.com.felipe.pessoal.sistema.ordem_servico.service.OrdemServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrdemServicoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class OrdemServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrdemServicoService serviceMock;

    private List<OrdemServico> listaOrdemServico;

    private final String url = "/servicos";

    private final String dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS";

    @BeforeEach()
    public void beforeEach(){
        Cliente cliente1 = new Cliente("Felipe", "12345678901", "Rua dos botucatus 57");
        Cliente cliente2 = new Cliente("Joana", "78965432101", "Alameda dos japorus 973");
        Cliente cliente3 = new Cliente("Mario", "78945698401", "Avenida das rainhas 5");

        cliente1.setId(1L);
        cliente2.setId(3L);
        cliente3.setId(5L);

        Objeto objeto1 = new Objeto(1L, "Marelli", "IAW 1G7");
        Objeto objeto2 = new Objeto(3L, "Bosch", "ME744");

        this.listaOrdemServico = new ArrayList<>();

        this.listaOrdemServico.add(new OrdemServico(LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(4L), cliente1, objeto2));
        this.listaOrdemServico.add(new OrdemServico(LocalDateTime.now().plusDays(4L), LocalDateTime.now().plusDays(5L), cliente2, objeto2));
        this.listaOrdemServico.add(new OrdemServico(LocalDateTime.now().plusDays(3L), LocalDateTime.now().plusDays(8L), cliente1, objeto1));
    }

    @Test
    void exibirOrdens_deveriaRetornarListaOrdemDto() throws Exception {
        when(serviceMock.exibirOrdens()).thenReturn(this.listaOrdemServico);

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String retornoListaOrdensJSON = mvcResult.getResponse().getContentAsString();
        String listaOrdensExperadaJSON = mapper.writeValueAsString(this.listaOrdemServico.stream().map(OrdemDTO::new));

        assertThat(retornoListaOrdensJSON).isEqualToIgnoringWhitespace(listaOrdensExperadaJSON);
    }

    @Test
    void exibirOrdens_deveriaRetornarListaVazia_DBVazio() throws Exception {
        when(serviceMock.exibirOrdens()).thenReturn(new ArrayList<OrdemServico>());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void cadastrarOrdem_deveriaRetornarOrdemCadastrada() throws Exception{
        OrdemServico ordem = this.listaOrdemServico.get(0);
        OrdemServicoForm formCadastro = new OrdemServicoForm(ordem.getCliente().getId(), ordem.getAparelho().getId());
        ordem.setId(5L);

        when(serviceMock.cadastrarOrdem(any())).thenReturn(ordem);
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(ordem.getId().intValue())))
                .andExpect(jsonPath("$.dataEntrada",
                        is(ordem.getDataEntrada().format(DateTimeFormatter.ofPattern(this.dateTimePattern)))))
                .andExpect(jsonPath("$.dataEntrega",
                        is(ordem.getDataEntrega().format(DateTimeFormatter.ofPattern(this.dateTimePattern)))))
                .andExpect(jsonPath("$.cliente.nome", is(ordem.getCliente().getNome())))
                .andExpect(jsonPath("$.aparelho.marca", is(ordem.getAparelho().getMarca())));
    }

    @Test
    void cadastrarOrdem_deveriaRetornarException_EntityNotFound() throws Exception{
        OrdemServico ordem = this.listaOrdemServico.get(0);
        OrdemServicoForm formCadastro = new OrdemServicoForm(ordem.getCliente().getId(), ordem.getAparelho().getId());

        when(serviceMock.cadastrarOrdem(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cadastrarOrdem_deveriaRetornarException_ErroBD() throws Exception{
        OrdemServico ordem = this.listaOrdemServico.get(0);
        OrdemServicoForm formCadastro = new OrdemServicoForm(ordem.getCliente().getId(), ordem.getAparelho().getId());

        when(serviceMock.cadastrarOrdem(any())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletarOrdem_deveriaRetornarOrdemDTODeletada() throws Exception {
        OrdemServico ordem = this.listaOrdemServico.get(1);
        ordem.setId(2L);
        when(serviceMock.deletarOrdem(ordem.getId())).thenReturn(ordem);

        mockMvc.perform(delete(url).param("id", ordem.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ordem.getId().intValue())))
                .andExpect(jsonPath("$.dataEntrada",
                        is(ordem.getDataEntrada().format(DateTimeFormatter.ofPattern(dateTimePattern)))))
                .andExpect(jsonPath("$.dataEntrega",
                        is(ordem.getDataEntrega().format(DateTimeFormatter.ofPattern(dateTimePattern)))))
                .andExpect(jsonPath("$.cliente.id", is(ordem.getCliente().getId().intValue())))
                .andExpect(jsonPath("$.aparelho.id", is(ordem.getAparelho().getId().intValue())));
    }

    @Test
    void deletarOrdem_deveriaRetornarException_OrdemNaoEncontrada() throws Exception {
        when(serviceMock.deletarOrdem(any()))
                .thenThrow(new EntityNotFoundException("Ordem de serviço nao encontrada no Banco de Dados."));

        mockMvc.perform(delete(url).param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.localizedMessage",
                        is("Ordem de serviço nao encontrada no Banco de Dados.")));
    }

    @Test
    void deletarOrdem_deveriaRetornarException_ErroAoDeletar() throws Exception {
        when(serviceMock.deletarOrdem(any()))
                .thenThrow(new IllegalArgumentException("Impossível deletar essa Ordem de Serviço."));

        mockMvc.perform(delete(url).param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.localizedMessage",
                        is("Impossível deletar essa Ordem de Serviço.")));
    }

    @Test
    void atualizarOrdem_deveriaRetornarOrdemAtualizada() throws Exception{
        OrdemServico ordem = this.listaOrdemServico.get(2);
        ordem.setId(5L);
        OrdemServicoAtualizadaForm formAtualizada =
                new OrdemServicoAtualizadaForm(ordem.getCliente().getId(), ordem.getAparelho().getId());

        when(serviceMock.atualizarOrdem(any())).thenReturn(ordem);

        mockMvc.perform(put(url)
        				.contentType(MediaType.APPLICATION_JSON)
        				.content(mapper.writeValueAsString(formAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ordem.getId().intValue())))
                .andExpect(jsonPath("$.dataEntrada",
                        is(ordem.getDataEntrada().format(DateTimeFormatter.ofPattern(dateTimePattern)))))
                .andExpect(jsonPath("$.dataEntrega",
                        is(ordem.getDataEntrega().format(DateTimeFormatter.ofPattern(dateTimePattern)))))
                .andExpect(jsonPath("$.cliente.id", is(ordem.getCliente().getId().intValue())))
                .andExpect(jsonPath("$.aparelho.id", is(ordem.getAparelho().getId().intValue())));
    }
    
    @Test
    void atualizarOrdem_deveriaRetornarException_OrdemNaoEncontrada() throws Exception{
        OrdemServico ordem = this.listaOrdemServico.get(2);
        OrdemServicoAtualizadaForm formAtualizada =
                new OrdemServicoAtualizadaForm(ordem.getCliente().getId(), ordem.getAparelho().getId());

        when(serviceMock.atualizarOrdem(any())).thenThrow(RuntimeException.class);

        mockMvc.perform(put(url)
        				.contentType(MediaType.APPLICATION_JSON)
        				.content(mapper.writeValueAsString(formAtualizada)))
        		.andExpect(status().isBadRequest());
    }

}