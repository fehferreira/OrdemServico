package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.OrdemDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoAtualizadaForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.OrdemRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/servicos")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemService;

    @GetMapping
    @ResponseBody
    public Page<OrdemDTO> exibirOrdens(@PageableDefault Pageable paginacao){
        return ordemService.exibirOrdens(paginacao);
    }

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<OrdemDTO> cadastrarOrdem(@RequestBody OrdemServicoForm formCadastro, UriComponentsBuilder uri){
        return ordemService.cadastrarOrdem(formCadastro,uri);
    }

    @DeleteMapping
    public ResponseEntity<OrdemDTO> deletarOrdem(@RequestParam Long id){
        return ordemService.deletarOrdem(id);
    }


    @PutMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<OrdemDTO> atualizarOrdem(@RequestBody OrdemServicoAtualizadaForm formAtualizado){
        return ordemService.atualizarOrdem(formAtualizado);
    }

}