package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoAtualizadoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.NenhumObjetoCadastradoException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ObjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/objetos")
public class ObjetoController {

    @Autowired
    private ObjetoService objetoService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ObjetoDTO>> exibirAparelhos(){
        List<Objeto> listaObjetos;
        try{
            listaObjetos = objetoService.exibirObjetos();
        }catch (NenhumObjetoCadastradoException exception){
            return new ResponseEntity(exception, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(listaObjetos.stream().map(ObjetoDTO::new), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ObjetoDTO> cadastrarObjeto(@RequestBody ObjetoCadastradoForm formNovoObjeto, UriComponentsBuilder uriBuilder){
        Objeto objetoCriado;
        try{
            objetoCriado = objetoService.cadastrarObjeto(formNovoObjeto);
        }catch(IllegalArgumentException | ObjetoExistenteException exception){
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }

        URI uri = uriBuilder.path("/objeto/${id}").buildAndExpand(objetoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new ObjetoDTO(objetoCriado));
    }

    @DeleteMapping
    public ResponseEntity<ObjetoDTO> deletarObjeto(@RequestParam Long id){
        ObjetoDTO objetoDeletado;
        try{
            objetoDeletado = new ObjetoDTO(objetoService.deletarObjeto(id));
        }catch(ObjetoInexistenteException exception){
            return new ResponseEntity(exception,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(objetoDeletado, HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ObjetoDTO> alterarObjeto(@RequestBody ObjetoAtualizadoForm formAtualizado, UriComponentsBuilder uriBuilder){
        Objeto objetoAtualizado;

        try{
            objetoAtualizado = objetoService.alterarObjeto(formAtualizado);
        }catch(ObjetoInexistenteException exception){
            return new ResponseEntity(exception, HttpStatus.NOT_FOUND);
        }

        URI uri = uriBuilder.path("/objeto/${id}").buildAndExpand(objetoAtualizado.getId()).toUri();
        return ResponseEntity.created(uri).body(new ObjetoDTO(objetoAtualizado));
    }

}
