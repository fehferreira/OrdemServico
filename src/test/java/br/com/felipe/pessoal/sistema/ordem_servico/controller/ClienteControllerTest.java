package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteServiceMock;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void criaUmaRequisicaoParaListaDeClientes_retornaUmaListaDeClienteDTO(){
        List<Cliente> clientes = criarListaClientes();
        Mockito.when(clienteServiceMock.listarClientes(Mockito.any())).thenReturn(clientes);

        List<ClienteDto> retornoClientesDto = clienteController.listarClientes(null);

        assertEquals(clientes.size(), retornoClientesDto.size());
        assertEquals(clientes.get(0).getId(), retornoClientesDto.get(0).getId());
        assertEquals(clientes.get(0).getNome(), retornoClientesDto.get(0).getNome());
        assertEquals(clientes.get(0).getEndereco(), retornoClientesDto.get(0).getEndereco());
        assertEquals(clientes.get(clientes.size()-1).getEndereco(), retornoClientesDto.get(retornoClientesDto.size()-1).getEndereco());
        assertEquals(clientes.get(clientes.size()-1).getEndereco(), retornoClientesDto.get(retornoClientesDto.size()-1).getEndereco());
        assertEquals(clientes.get(clientes.size()-1).getEndereco(), retornoClientesDto.get(retornoClientesDto.size()-1).getEndereco());
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