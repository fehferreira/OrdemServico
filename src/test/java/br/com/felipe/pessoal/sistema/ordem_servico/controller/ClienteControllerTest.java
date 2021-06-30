package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ClienteController.class)
@ActiveProfiles("test")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClienteService serviceMock;

    @Test
    void criaUmaRequisicaoParaListaDeClientes_retornaUmaListaDeClienteDTO() throws Exception{
        List<Cliente> clientes = criarListaClientes();

        when(serviceMock.listarClientes(any())).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk());
    }

    private List<Cliente> criarListaClientes(){
        Cliente cliente1 = new Cliente("Felipe", "12345678901", "Rua dos botucatus 57");
        Cliente cliente2 = new Cliente("Joana", "78965432101", "Alameda dos japorus 973");
        Cliente cliente3 = new Cliente("Mario", "78945698401", "Avenida das rainhas 5");

        cliente1.setId(1L);
        cliente2.setId(3L);
        cliente3.setId(5L);

        return List.of(cliente1, cliente2, cliente3);
    }

}