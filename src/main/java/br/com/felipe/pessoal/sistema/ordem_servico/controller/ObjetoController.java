package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoAtualizadoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ObjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Controller
@RequestMapping("/objeto")
public class ObjetoController {

    @Autowired
    private ObjetoRepository objetoRepository;

    @Autowired
    private ObjetoService objetoService;

    @GetMapping
    @ResponseBody
    public Page<ObjetoDTO> exibirAparelhos(@PageableDefault Pageable paginacao){
        return ObjetoDTO.converterObjetos(objetoRepository.findAll(paginacao));
    }

    @PostMapping
    public ResponseEntity<ObjetoDTO> cadastrarObjeto(@RequestBody ObjetoCadastradoForm formNovoObjeto, UriComponentsBuilder uri){
        ObjetoDTO objetoCriado = null;
        try{
            objetoCriado = objetoService.cadastrarObjeto(formNovoObjeto);
        }catch(IllegalArgumentException | ObjetoExistenteException exception){
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(objetoCriado, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ObjetoDTO> deletarObjeto(@RequestParam Long id){
        return objetoService.deletarObjeto(id);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ObjetoDTO> alterarObjeto(@RequestBody ObjetoAtualizadoForm formAtualizado, UriComponentsBuilder uri){
        return objetoService.alterarObjeto(formAtualizado,uri);
    }

}
