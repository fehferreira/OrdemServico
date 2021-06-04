package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

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

        ResponseEntity<ObjetoDTO> objetoDTOResponseEntity =
                objetoService.cadastrarObjeto(objetoForm, uriBuilder);

        assertEquals(HttpStatus.CREATED,objetoDTOResponseEntity.getStatusCode());
        assertEquals(objetoCadastrado.getMarca(),objetoDTOResponseEntity.getBody().getMarca());
        assertEquals(objetoCadastrado.getModelo(),objetoDTOResponseEntity.getBody().getModelo());
    }

    @Test
    void enviaUmObjetoEJogaUmaException_retornaOTratamentoDaException(){
        ObjetoCadastradoForm objetoForm = new ObjetoCadastradoForm("Delphi","MT60");

        Mockito.when(objetoRepositoryMock.save(Mockito.any())).thenThrow(RuntimeException.class);
        try {
            objetoService.cadastrarObjeto(objetoForm, uriBuilder);
        }catch (Exception exception){
            assertEquals(RuntimeException.class,exception.getClass());
        }
    }

    @Test
    void enviaUmObjetoComMarcaEModeloJaCadastrados_retornaUmaExceptionEspecial(){
        ObjetoCadastradoForm objetoForm = new ObjetoCadastradoForm("Delphi","MT60");
        Mockito.when(objetoRepositoryMock.save(Mockito.any())).thenThrow(ObjetoExistenteException.class);
        try{
            objetoService.cadastrarObjeto(objetoForm,uriBuilder);
        }catch (Exception exception){
            assertEquals(ObjetoExistenteException.class, exception.getClass());
        }
    }
    
}