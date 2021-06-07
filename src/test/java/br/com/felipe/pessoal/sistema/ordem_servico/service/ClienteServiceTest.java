package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @InjectMocks
    private ClienteService clienteService;

    private UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    @Test
    void solicitaUmaListaDeClientesSemFiltroDeNome_retornaUmaListaDeClientes(){
        List<Cliente> clientes = criaListaClientes();

        Mockito.when(clienteRepositoryMock.findAll()).thenReturn(clientes);
        List<Cliente> listaRetorno = clienteService.listarClientes(null);

        assertEquals(clientes, listaRetorno);

    }

    

    private List<Cliente> criaListaClientes(){
        Cliente cliente1 = new Cliente("Felipe Ferreira", "12345678901", "Rua dos Jequitibás");
        Cliente cliente2 = new Cliente("Maria Antonieta", "98765432101", "Avenida dos Manguaris");
        Cliente cliente3 = new Cliente("Rafael Carlos Dias", "78965412303", "Travessa dos Vizinhos");

        cliente1.setId(1L);
        cliente1.setId(2L);
        cliente1.setId(3L);

        List<Cliente> clientes = List.of(cliente1,cliente2,cliente3);
        return clientes;
    }

}