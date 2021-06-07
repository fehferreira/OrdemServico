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

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void solicitaUmaListaDeClientesComFiltroDeNome_retornaUmaListaFiltrada(){
        List<Cliente> clientes = criaListaClientes();
        String valorFiltro = "Felipe Ferreira";

        Mockito.when(clienteRepositoryMock.findAllByNome(valorFiltro))
                .thenReturn(clientes.stream().filter(cliente -> cliente.getNome().contains(valorFiltro)).collect(Collectors.toList()));

        List<Cliente> listaRetorno = clienteService.listarClientes(valorFiltro);

        List<Cliente> listaCliente = List.of(clientes.get(0));

        assertEquals(listaCliente.size(),listaRetorno.size());
        assertEquals(listaCliente.get(0).getId(),listaRetorno.get(0).getId());
        assertEquals(listaCliente.get(0).getNome(),listaRetorno.get(0).getNome());
        assertEquals(listaCliente.get(0).getCpf(),listaRetorno.get(0).getCpf());
        assertEquals(listaCliente.get(0).getEndereco(),listaRetorno.get(0).getEndereco());
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