package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveriaValidarARequisicaoDeListaDeClientes() throws Exception {
        URI uri = new URI("/clientes");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(uri))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}