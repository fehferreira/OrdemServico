package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.OrdemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrdemServiceTest {

    @Mock
    private OrdemRepository ordemRepositoryMock;

    @Mock
    private ClienteService clienteServiceMock;

    @Mock
    private ObjetoService objetoServiceMock;

    @Captor
    private ArgumentCaptor<OrdemServico> ordemCaptor;

    @InjectMocks
    private OrdemServicoService ordemService;


    @Test
    void solicitaTodasAsOrdensDeServico_retornaUmaListaDeOrdens(){
        List<OrdemServico> ordens = criarListaOrdemServico();

        Mockito.when(ordemRepositoryMock.findAll()).thenReturn(ordens);
        List<OrdemServico> ordensRetorno = ordemService.exibirOrdens();

        assertEquals(ordens.get(0).getId(), ordensRetorno.get(0).getId());
        assertEquals(ordens.get(0).getDataEntrada(), ordensRetorno.get(0).getDataEntrada());
        assertEquals(ordens.get(0).getDataEntrega(), ordensRetorno.get(0).getDataEntrega());
        assertEquals(ordens.get(0).getCliente(), ordensRetorno.get(0).getCliente());
        assertEquals(ordens.get(0).getAparelho(), ordensRetorno.get(0).getAparelho());

    }

    @Test
    void solicitaAsOrdensDeServicoDeUmDBVazio_retornaUmaListaVazia(){
        List<OrdemServico> ordemServicos = ordemService.exibirOrdens();
        assertTrue(ordemServicos.isEmpty());
    }

    @Test
    void solicitaADelecaoDeUmaOrdem_retornaUmaOrdemDeletada(){
        OrdemServico ordem = criarOrdemServico();
        Mockito.when(ordemRepositoryMock.findById(ordem.getId())).thenReturn(Optional.of(ordem));
        OrdemServico retornoOrdem = ordemService.deletarOrdem(ordem.getId());

        assertEquals(ordem.getId(), retornoOrdem.getId());
        assertEquals(ordem.getDataEntrada(), retornoOrdem.getDataEntrada());
        assertEquals(ordem.getDataEntrega(), retornoOrdem.getDataEntrega());
        assertEquals(ordem.getCliente(), retornoOrdem.getCliente());
        assertEquals(ordem.getAparelho(), retornoOrdem.getAparelho());
    }

    @Test
    void solicitaADelecaoDeUmaOrdemInexistente_retornaUmaExcessao(){
        Mockito.when(ordemRepositoryMock.findById(Mockito.any())).thenThrow(EntityNotFoundException.class);
        try {
            ordemService.deletarOrdem(1L);
        }catch (Exception exception){
            assertEquals(EntityNotFoundException.class, exception.getClass());
        }
        Mockito.verify(ordemRepositoryMock).findById(Mockito.any());
    }

    @Test

    void solicitaACriacaoDeUmaOrdem_retornaAOrdemCriada(){
        Cliente clienteCadastrado = criaClienteGenerico();
        clienteCadastrado.setId(1L);
        Objeto objetoCadastrado = criaObjetoGenerico();

        Mockito.when(clienteServiceMock.detalharCliente(clienteCadastrado.getId())).thenReturn(clienteCadastrado);
        Mockito.when(objetoServiceMock.detalharObjeto(objetoCadastrado.getId())).thenReturn(objetoCadastrado);

        OrdemServicoForm formCadastro = new OrdemServicoForm(clienteCadastrado.getId(), objetoCadastrado.getId());
        ordemService.cadastrarOrdem(formCadastro);

        Mockito.verify(ordemRepositoryMock).save(ordemCaptor.capture());

        OrdemServico ordemCadastrada = ordemCaptor.getValue();

        assertEquals(clienteCadastrado, ordemCadastrada.getCliente());
        assertEquals(objetoCadastrado, ordemCadastrada.getAparelho());
    }


    @Test
    void solicitaACriacaoDeUmaOrdemComErroDeSalvamento_retornaUmException(){
        Mockito.when(ordemRepositoryMock.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        OrdemServicoForm form = new OrdemServicoForm(1L,5L);
        try{
            ordemService.cadastrarOrdem(form);
        }catch (Exception exception){
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }
        Mockito.verify(ordemRepositoryMock).save(Mockito.any());
    }

    @Test
    void solicitaACriacaoDeUmaOrdemIncompleta_retornaUmaException(){
        OrdemServicoForm form = new OrdemServicoForm(null, null);
        try{
            ordemService.cadastrarOrdem(form);
        }catch (Exception exception){
            assertEquals(EntityNotFoundException.class, exception.getClass());
        }
        Mockito.verify(ordemRepositoryMock).save(Mockito.any());
    }


    private Cliente criaClienteGenerico(){
        return new Cliente("Felipe Ferreira", "12345678901", "Av. Tamanduateí 58");
    }

    private Objeto criaObjetoGenerico(){
        return new Objeto(5L, "Marelli", "IAW 7GF");
    }

    private OrdemServico criarOrdemServico() {
        Cliente cliente = criaClienteGenerico();
        Objeto objeto = criaObjetoGenerico();
        return new OrdemServico(LocalDateTime.now(), LocalDateTime.now().plusDays(2L), cliente, objeto);
    }


    private List<OrdemServico> criarListaOrdemServico(){
        Cliente cliente1 = new Cliente("Felipe", "12398745698", "Av. dos tamanduateís 54");
        Cliente cliente2 = new Cliente("Armando", "97854589654", "Rua das tramóias 948");
        cliente1.setId(1L);
        cliente2.setId(50L);

        Objeto objeto1 = new Objeto(1L, "Marelli", "IAW 1AVP");
        Objeto objeto2 = new Objeto(20L, "Bosch", "ME796");

        List<OrdemServico> ordens = new ArrayList<>();
        ordens.add(new OrdemServico(LocalDateTime.now(), null, cliente1, objeto2));
        ordens.add(new OrdemServico(LocalDateTime.now(), null, cliente2, objeto1));
        ordens.add(new OrdemServico(LocalDateTime.now(), null, cliente1, objeto1));

        return ordens;
    }

}