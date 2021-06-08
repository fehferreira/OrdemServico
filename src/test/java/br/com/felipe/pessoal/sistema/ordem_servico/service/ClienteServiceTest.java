package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.CadastrarClienteForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Test
    void solicitaUmaListaDeClientesEmUmBDVazio_retornaUmaListaVazia(){
        List<Cliente> clientes = clienteService.listarClientes(null);
        assertTrue(clientes.isEmpty());
    }

    @Test
    void solicitaUmClienteDetalhado_retornaUmClienteDetalhado(){
        Cliente cliente = new Cliente("Felipe Ferreira", "12345678901", "Rua dos Jequitibás");
        cliente.setId(97L);

        Mockito.when(clienteRepositoryMock.findById(97L)).thenReturn(Optional.of(cliente));
        Cliente clienteDetalhado = clienteService.detalharCliente(97L);

        assertEquals(cliente.getId(), clienteDetalhado.getId());
        assertEquals(cliente.getNome(), clienteDetalhado.getNome());
        assertEquals(cliente.getCpf(), clienteDetalhado.getCpf());
        assertEquals(cliente.getEndereco(), clienteDetalhado.getEndereco());
    }

    @Test
    void solicitaUmClienteDetalhadoSemPassarOID_retornaUmaExcessao(){
        try{
            Mockito.when(clienteRepositoryMock.findById(Mockito.any())).thenThrow(IllegalArgumentException.class);
            clienteService.detalharCliente(null);
        }catch (Exception exception){
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        Mockito.verify(clienteRepositoryMock).findById(Mockito.any());
    }

    @Test
    void solicitaUmClienteInexistenteNoBanco_retornaUmaExcessao(){
        Cliente cliente = new Cliente("Felipe Ferreira", "12345678901", "Rua dos Jequitibás");
        cliente.setId(5L);

        try{
            Mockito.when(clienteRepositoryMock.findById(Mockito.any())).thenReturn(Optional.empty());
            clienteService.detalharCliente(1L);
        }catch (Exception exception){
            assertEquals(ClienteInexistenteException.class, exception.getClass());
        }

        Mockito.verify(clienteRepositoryMock).findById(Mockito.any());
    }

    @Test
    void solicitaOCadastroDeUmCliente_retornaOClienteCadastradoComSeuId(){
        Cliente clienteComId = new Cliente("Felipe Ferreira", "12345678901", "Rua dos Jequitibás");
        Cliente clienteSemId = clienteComId;
        clienteComId.setId(5L);

        Mockito.when(clienteRepositoryMock.save(Mockito.any(Cliente.class))).thenReturn(clienteComId);
        Cliente clienteRetorno = clienteService.cadastrarCliente(clienteSemId);

        assertEquals(clienteComId.getId(), clienteRetorno.getId());
        assertEquals(clienteComId.getNome(), clienteRetorno.getNome());
        assertEquals(clienteComId.getCpf(), clienteRetorno.getCpf());
        assertEquals(clienteComId.getEndereco(), clienteRetorno.getEndereco());
    }

    @Test
    void solicitaOCadastroDeUmClienteExistente_retornaUmaExcessaoEspecial(){
        Cliente clienteComId = new Cliente("Felipe Ferreira", "12345678901", "Rua dos Jequitibás");
        clienteComId.setId(5L);

        Mockito.when(clienteRepositoryMock.findByNome(clienteComId.getNome())).thenReturn(Optional.of(clienteComId));
        try{
            clienteService.cadastrarCliente(clienteComId);
        }catch (Exception exception){
            assertEquals(ClienteExistenteException.class, exception.getClass());
        }
        Mockito.verify(clienteRepositoryMock).findByNome(Mockito.any());
    }

    @Test
    void solicitaAAtualizacaoDoCliente_retornaOClienteAtualizado(){
        CadastrarClienteForm form = criarFormDeCadastro();

        Cliente clienteAntigo = new Cliente("Rogerio Almeida", "78965412303", "Rua dos palmares");
        clienteAntigo.setId(57L);

        Mockito.when(clienteRepositoryMock.getOne(57L)).thenReturn(clienteAntigo);
        Cliente clienteAtualizado = clienteService.atualizarCliente(57L, form);

        assertEquals(57L,clienteAtualizado.getId());
        assertEquals(form.getNome(),clienteAtualizado.getNome());
        assertEquals(form.getCpf(),clienteAtualizado.getCpf());
        assertEquals(form.getEndereco(),clienteAtualizado.getEndereco());
    }

    @Test
    void solicitaAAtualizacaoDoClienteQueNaoExiste_retornaUmaExcessao(){
        Mockito.when(clienteRepositoryMock.getOne(Mockito.any())).thenThrow(EntityNotFoundException.class);
        try{
            clienteService.atualizarCliente(Mockito.any(), criarFormDeCadastro());
        }catch (Exception exception){
            assertEquals(EntityNotFoundException.class,exception.getClass());
        }
        Mockito.verify(clienteRepositoryMock).getOne(Mockito.any());
    }

    @Test
    void solicitaAtualizacaoParcialDoCliente_retornaUmCLienteParcialmenteAtualizado(){
        CadastrarClienteForm form = criarFormDeCadastro();
        form.setCpf("");
        form.setEndereco("");

        Cliente clienteAntigo = new Cliente("Rogerio Almeida", "78965412303", "Rua dos palmares");
        clienteAntigo.setId(57L);

        Mockito.when(clienteRepositoryMock.getOne(57L)).thenReturn(clienteAntigo);
        Cliente clienteAtualizado = clienteService.atualizarCliente(57L, form);

        assertEquals(57L,clienteAtualizado.getId());
        assertEquals(form.getNome(),clienteAtualizado.getNome());
        assertNotEquals(form.getCpf(),clienteAtualizado.getCpf());
        assertNotEquals(form.getEndereco(),clienteAtualizado.getEndereco());

        form.setCpf("12365479884");

        clienteAtualizado = clienteService.atualizarCliente(57L, form);

        assertEquals(57L,clienteAtualizado.getId());
        assertEquals(form.getNome(),clienteAtualizado.getNome());
        assertEquals(form.getCpf(),clienteAtualizado.getCpf());
        assertNotEquals(form.getEndereco(),clienteAtualizado.getEndereco());

        form.setEndereco("Rua dos tamanduateís 54");

        clienteAtualizado = clienteService.atualizarCliente(57L, form);

        assertEquals(57L,clienteAtualizado.getId());
        assertEquals(form.getNome(),clienteAtualizado.getNome());
        assertEquals(form.getCpf(),clienteAtualizado.getCpf());
        assertEquals(form.getEndereco(),clienteAtualizado.getEndereco());
    }

    @Test
    void solicitaUmaDelecaoDeUmCLienteComParametroID_retornaOCLienteDeletado(){
        Cliente clienteDeletado = new Cliente("Felipe Ferreira", "12345678901", "Rua dos Jequitibás");
        clienteDeletado.setId(1L);

        Mockito.when(clienteRepositoryMock.findById(1L)).thenReturn(Optional.of(clienteDeletado));
        Cliente retornoCliente = clienteService.deletarCliente(1L);

        assertEquals(clienteDeletado.getId(),retornoCliente.getId());
        assertEquals(clienteDeletado.getNome(),retornoCliente.getNome());
        assertEquals(clienteDeletado.getEndereco(),retornoCliente.getEndereco());
        assertEquals(clienteDeletado.getCpf(),retornoCliente.getCpf());
    }

    @Test
    void solicitaUmaDelecaoDeUmClienteInexistente_retonarUmaExcessao(){
        try {
            Mockito.when(clienteRepositoryMock.findById(1L)).thenReturn(Optional.empty());
            clienteService.deletarCliente(1L);
        }catch (Exception exception){
            assertEquals(NoSuchElementException.class, exception.getClass());
        }
        Mockito.verify(clienteRepositoryMock).findById(Mockito.any());
    }

    private CadastrarClienteForm criarFormDeCadastro(){
        CadastrarClienteForm form = new CadastrarClienteForm();
        form.setNome("Felipe Ferreira");
        form.setCpf("98765432132");
        form.setEndereco("Alameda dos alagoanos");
        return form;
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