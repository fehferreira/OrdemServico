package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest{

    private final String uriClientes = "/clientes";

    private Faker faker = new Faker(new Locale("pt-BR"));

    private ObjectMapper objectMapper;

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeTestClass
    public void setup(){
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(clienteController)
                .build();
    }

    @Test
    public void deveriaValidarARequisicaoDeListaDeClientes() throws Exception {
        URI uri = new URI(uriClientes);

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveriaValidarOsParametrosRecebidosViaGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].nome").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].endereco").exists());
    }

    @Test
    public void deveriaRetornarAQuantidadeDeUsuariosCerta() throws Exception {
        List<Cliente> listaClientes = clienteRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes))
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfElements", Matchers.is(listaClientes.size())));
    }

}