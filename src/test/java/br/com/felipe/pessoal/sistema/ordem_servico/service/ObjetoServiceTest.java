package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
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
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

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
    void enviaUmObjetoEUmaUriBuilder_retornaOObjetoCadastradoJuntoCom201Status(){
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
    void enviaUmId_retornaHttpStatus200(){
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
    
}