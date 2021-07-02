package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import java.net.URI;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.OrdemDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoAtualizadaForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import br.com.felipe.pessoal.sistema.ordem_servico.service.OrdemServicoService;

@Controller
@RequestMapping("/servicos")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<OrdemDTO> exibirOrdens(){
        return new ResponseEntity(OrdemDTO.converterOrdens(ordemService.exibirOrdens()), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<OrdemDTO> cadastrarOrdem(@RequestBody OrdemServicoForm formCadastro, UriComponentsBuilder uriBuilder){
        try {
            OrdemServico ordemServicoCadastrada = ordemService.cadastrarOrdem(formCadastro);
            OrdemDTO novaOrdemDTO = new OrdemDTO(ordemServicoCadastrada);
            URI uri = uriBuilder.path("/servicos/${id}").buildAndExpand(novaOrdemDTO.getId()).toUri();
            return ResponseEntity.created(uri).body(novaOrdemDTO);
        }catch(EntityNotFoundException | IllegalArgumentException exception){
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<OrdemDTO> deletarOrdem(@RequestParam Long id){
        try{
            return ResponseEntity.ok(new OrdemDTO(ordemService.deletarOrdem(id)));
        }catch (EntityNotFoundException | IllegalArgumentException exception){
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<OrdemDTO> atualizarOrdem(@RequestBody OrdemServicoAtualizadaForm formAtualizado){
        try{
            return ResponseEntity.ok(new OrdemDTO(ordemService.atualizarOrdem(formAtualizado)));
        }catch (RuntimeException exception){
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }
    }
}
