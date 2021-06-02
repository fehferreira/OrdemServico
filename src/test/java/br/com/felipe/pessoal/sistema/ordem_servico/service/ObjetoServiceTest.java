package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

class ObjetoServiceTest {

    @Mock
    private ObjetoRepository objetoRepositoryMock;

    @Mock
    private UriComponentsBuilder uriBuilderMock;

    private UriComponentsBuilder uriBuilder;
    
    private ObjetoService objetoService;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
        this.objetoService = new ObjetoService(objetoRepositoryMock);
        this.uriBuilder = new UriComponentsBuilder("/aparelho");
    }

    @Test
    void deveriaCadastrarUmObjeto(){
        ObjetoCadastradoForm objetoForm = new ObjetoCadastradoForm("Delphi","MT60");
        Objeto objetoCadastrado = objetoForm.retornarObjeto();
        URI uri = uriBuilder.path("/aparelho/${id}").buildAndExpand(objetoCadastrado.getId()).toUri();

        Mockito.when(uriBuilderMock.path(Mockito.anyString()).buildAndExpand(Mockito.any(Objeto.class)).toUri())
                .thenReturn(uri);

        ResponseEntity<ObjetoDTO> objetoDTOResponseEntity =
                objetoService.cadastrarObjeto(objetoForm, uriBuilderMock);

        Assertions.assertEquals(
                ResponseEntity.created(uri).body(new ObjetoDTO(objetoCadastrado)), objetoDTOResponseEntity);
    }
    
}