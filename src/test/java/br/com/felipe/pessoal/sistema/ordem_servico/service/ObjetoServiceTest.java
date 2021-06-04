package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ObjetoServiceTest {

    @Mock
    private ObjetoRepository objetoRepositoryMock;

    private UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    @InjectMocks
    private ObjetoService objetoService;2

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
    
}