package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoAtualizadoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ObjetoServiceTest {

    @Mock
    private ObjetoRepository objetoRepositoryMock;

    @InjectMocks
    private ObjetoService objetoService;

    private UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    @Test
    void enviaUmObjeto_retornaOObjetoCadastrado(){
        ObjetoCadastradoForm objetoForm = new ObjetoCadastradoForm("Delphi","MT60");
        Objeto objetoCadastrado = objetoForm.retornarObjeto();
        objetoCadastrado.setId(1L);

        Mockito.when(objetoRepositoryMock.save(Mockito.any())).thenReturn(objetoCadastrado);

        Objeto retornoObjetoService = objetoService.cadastrarObjeto(objetoForm);

        assertEquals(objetoCadastrado.getMarca(),retornoObjetoService.getMarca());
        assertEquals(objetoCadastrado.getModelo(),retornoObjetoService.getModelo());
    }

    @Test
    void enviaUmObjetoEJogaUmaException_retornaOTratamentoDaException(){
        ObjetoCadastradoForm objetoForm = new ObjetoCadastradoForm("Delphi","MT60");

        Mockito.when(objetoRepositoryMock.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        try {
            objetoService.cadastrarObjeto(objetoForm);
        }catch (Exception exception){
            assertEquals(IllegalArgumentException.class,exception.getClass());
        }
    }

    @Test
    void enviaUmObjetoComMarcaEModeloJaCadastrados_retornaUmaExceptionEspecial(){
        ObjetoCadastradoForm objetoForm = new ObjetoCadastradoForm("Delphi","MT60");
        Mockito.when(objetoRepositoryMock.findByMarcaAndModelo(Mockito.any())).thenThrow(ObjetoExistenteException.class);
        try{
            objetoService.cadastrarObjeto(objetoForm);
        }catch (Exception exception){
            assertEquals(ObjetoExistenteException.class, exception.getClass());
        }
    }

    @Test
    void enviaUmId_retornaOObjetoDeletado(){
        Objeto objetoSalvo = new Objeto("Delphi", "MT60");
        objetoSalvo.setId(1L);
        Mockito.when(objetoRepositoryMock.findById(objetoSalvo.getId())).thenReturn(Optional.of(objetoSalvo));

        Objeto objetoDeletado = objetoService.deletarObjeto(objetoSalvo.getId());

        assertEquals(objetoSalvo.getId(), objetoDeletado.getId());
        assertEquals(objetoSalvo.getModelo(), objetoDeletado.getModelo());
        assertEquals(objetoSalvo.getMarca(), objetoDeletado.getMarca());
    }

    @Test
    void enviaUmIdDeUmObjetoInexistente_retornaUmaExceptionsEspecial(){
        try{
            objetoService.deletarObjeto(1L);
        }catch(ObjetoInexistenteException exception){
            assertEquals(ObjetoInexistenteException.class, exception.getClass());
        }

        Mockito.verify(objetoRepositoryMock).findById(Mockito.any());
        Mockito.verifyNoMoreInteractions(objetoRepositoryMock);
    }

    @Test
    void enviaUmFormComObjetoParaAtualizar_retornaOObjetoAtualizado(){
        Objeto objetoOriginal = new Objeto("Marelli", "IAW 4AFB");
        objetoOriginal.setId(1L);

        ObjetoAtualizadoForm formObjeto = new ObjetoAtualizadoForm(objetoOriginal.getId(),"BOSCH","ME796");
        Objeto objetoAtualizado = formObjeto.retornarObjeto();

        Mockito.when(objetoRepositoryMock.findById(objetoOriginal.getId())).thenReturn(Optional.of(objetoOriginal));
        Mockito.when(objetoRepositoryMock.getOne(objetoOriginal.getId())).thenReturn(objetoOriginal);
        Objeto retornoObjetoService = objetoService.alterarObjeto(formObjeto);

        assertEquals(objetoAtualizado.getId(),retornoObjetoService.getId());
        assertEquals(objetoAtualizado.getMarca(),retornoObjetoService.getMarca());
        assertEquals(objetoAtualizado.getModelo(),retornoObjetoService.getModelo());
    }

    @Test
    void enviaUmFormSemId_retornaUmaException(){
        ObjetoAtualizadoForm formObjeto = new ObjetoAtualizadoForm(null,"BOSCH","ME796");

        try {
            objetoService.alterarObjeto(formObjeto);
        }catch (Exception exception){
            assertEquals(ObjetoInexistenteException.class, exception.getClass());
        }

        Mockito.verify(objetoRepositoryMock).findById(Mockito.any());
    }

    @Test
    void retornaUmaListaDeObjetos(){
        List<Objeto> objetos = criaListaObjetos();
        Mockito.when(objetoRepositoryMock.findAll()).thenReturn(objetos);
        List<Objeto> retornoObjetos = objetoService.exibirObjetos();
        assertEquals(objetos,retornoObjetos);
    }

    private List<Objeto> criaListaObjetos(){
        Objeto objeto1 = new Objeto(1L,"MARELLI","IAW 1G7");
        Objeto objeto2 = new Objeto(2L,"BOSCH","ME796");
        Objeto objeto3 = new Objeto(3L,"FORD","EEC-V");

        List<Objeto> objetos = new ArrayList<>();
        objetos.add(objeto1);
        objetos.add(objeto2);
        objetos.add(objeto3);

        return objetos;
    }

}