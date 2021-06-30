package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDetalhadoDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.CadastrarClienteForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ClienteService;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClienteService serviceMock;

    private List<Cliente> listaClientes;

    private final String url = "/clientes";

    @BeforeEach
    private void beforeEach(){
        this.listaClientes = new ArrayList<>();

        Cliente cliente1 = new Cliente("Felipe", "12345678901", "Rua dos botucatus 57");
        Cliente cliente2 = new Cliente("Joana", "78965432101", "Alameda dos japorus 973");
        Cliente cliente3 = new Cliente("Mario", "78945698401", "Avenida das rainhas 5");

        cliente1.setId(1L);
        cliente2.setId(3L);
        cliente3.setId(5L);

        this.listaClientes.add(cliente1);
        this.listaClientes.add(cliente2);
        this.listaClientes.add(cliente3);
    }

    @Test
    void listarClientes_deveriaRetornarUmaListaClientesDto() throws Exception{
        when(serviceMock.listarClientes(any())).thenReturn(listaClientes);

        MvcResult mvcResult = mockMvc.perform(get(this.url))
                .andExpect(status().isOk())
                .andReturn();

        String listaClientesJsonExperada = mapper.writeValueAsString(listaClientes.stream().map(ClienteDto::new));
        String retornoRequisicaoJson = mvcResult.getResponse().getContentAsString();

        assertThat(retornoRequisicaoJson).isEqualToIgnoringWhitespace(listaClientesJsonExperada);
    }

    @Test
    void detalharCliente_deveriaRetornarUmClienteDto() throws Exception{
        Cliente clienteCadastrado = this.listaClientes.get(0);
        ClienteDetalhadoDto clienteDetalhado = new ClienteDetalhadoDto(clienteCadastrado);

        when(serviceMock.detalharCliente(clienteCadastrado.getId())).thenReturn(clienteCadastrado);

        mockMvc.perform(get(this.url + "/" + clienteCadastrado.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clienteDetalhado.getId())))
                .andExpect(jsonPath("$.nome", is(clienteDetalhado.getNome())))
                .andExpect(jsonPath("$.endereco", is(clienteDetalhado.getEndereco())));
    }

    @Test
    void detalharCliente_solicitaClienteInexistente_deveriaRetornar404() throws Exception{
        when(serviceMock.detalharCliente(Mockito.any()))
                .thenThrow(new ClienteInexistenteException("Não foi encontrado este cliente no Banco de Dados!"));

        mockMvc.perform(get(this.url + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.localizedMessage",
                            is("Não foi encontrado este cliente no Banco de Dados!")));
    }

    @Test
    void cadastrarCliente_deveriaRetornarClienteDtoCadastrado() throws Exception {
        Cliente cliente = this.listaClientes.get(2);
        CadastrarClienteForm formCadastro = converterClienteCadastrarForm(cliente);
        cliente.setId(1L);

        when(serviceMock.cadastrarCliente(Mockito.any())).thenReturn(cliente);

        mockMvc.perform(post(this.url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(cliente.getId().intValue())))
                .andExpect(jsonPath("$.nome", is(cliente.getNome())))
                .andExpect(jsonPath("$.endereco", is(cliente.getEndereco())));
    }

    @Test
    void cadastrarCliente_deveriaRetornarException_ClienteJaExistente() throws Exception {
        Cliente cliente = this.listaClientes.get(1);
        CadastrarClienteForm formCadastro = converterClienteCadastrarForm(cliente);

        when(serviceMock.cadastrarCliente(Mockito.any()))
                .thenThrow(new ClienteExistenteException("Cliente já cadastrado no Banco de Dados!"));

        mockMvc.perform(post(this.url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.localizedMessage",
                        is("Cliente já cadastrado no Banco de Dados!")));
    }

    @Test
    void cadastrarCliente_deveriaRetornarException_ErroDB() throws Exception {
        Cliente cliente = this.listaClientes.get(0);
        CadastrarClienteForm formCadastro = converterClienteCadastrarForm(cliente);

        when(serviceMock.cadastrarCliente(Mockito.any()))
                .thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post(this.url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(formCadastro)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void atualizarCliente_deveriaRetornarClienteAtualizado() throws Exception{
        Cliente cliente = listaClientes.get(1);
        CadastrarClienteForm formAtualizacao = converterClienteCadastrarForm(cliente);

        when(serviceMock.atualizarCliente(Mockito.any(), Mockito.any())).thenReturn(cliente);

        MvcResult mvcResult = mockMvc.perform(put(this.url + "/" + cliente.getId().toString())
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(formAtualizacao)))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String clienteatualizadoJson = mvcResult.getResponse().getContentAsString();
        String clienteEsperadoJson = mapper.writeValueAsString(cliente);

        assertThat(clienteatualizadoJson).isEqualToIgnoringWhitespace(clienteEsperadoJson);
    }

    @Test
    void atualizarCliente_deveriaRetornarException_ClienteNaoEncontrado() throws Exception {
        Cliente cliente = listaClientes.get(1);
        CadastrarClienteForm formAtualizacao = converterClienteCadastrarForm(cliente);

        when(serviceMock.atualizarCliente(Mockito.any(), Mockito.any()))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put(this.url + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(formAtualizacao)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletarCliente_deveriaRetornarClienteDtoDeletado() throws Exception {
        Cliente cliente = this.listaClientes.get(0);

        when(serviceMock.deletarCliente(cliente.getId())).thenReturn(cliente);

        MvcResult mvcResult = mockMvc.perform(delete(url + "/" + cliente.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();

        String clienteDeletadoJson = mvcResult.getResponse().getContentAsString();
        String clienteEsperadoJson = mapper.writeValueAsString(new ClienteDto(cliente));

        assertThat(clienteDeletadoJson).isEqualToIgnoringWhitespace(clienteEsperadoJson);
    }

    @Test
    void deletarcliente_deveriaRetornarException_ClienteNaoEncontrado() throws Exception {
        when(serviceMock.deletarCliente(Mockito.any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete(url + "/1"))
                .andExpect(status().isNotFound());
    }

    private CadastrarClienteForm converterClienteCadastrarForm(Cliente clienteDescadastrado) {
        CadastrarClienteForm form = new CadastrarClienteForm();
        form.setNome(clienteDescadastrado.getNome());
        form.setCpf(clienteDescadastrado.getCpf());
        form.setEndereco(clienteDescadastrado.getEndereco());
        return form;
    }
}