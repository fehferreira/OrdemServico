package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.util.Locale;
import java.util.Optional;

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

    @BeforeTestMethod
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
    public void deveriaValidarOsValoresRecebidosDaListaDeClientes() throws Exception {
        String name = faker.name().toString();
        String cpf = String.valueOf(faker.number().numberBetween(111111111,999999999));
        String address = faker.address().fullAddress().toString();

        Cliente cliente = new Cliente(name,cpf,address);
        clienteRepository.save(cliente);

        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cliente.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(cliente.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endereco").value(cliente.getEndereco()));

        Mockito.verify(clienteRepository,Mockito.times(1)).findById(cliente.getId());
    }
}