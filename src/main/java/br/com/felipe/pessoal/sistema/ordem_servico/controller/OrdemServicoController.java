package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.OrdemDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.OrdemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/servicos")
public class OrdemServicoController {

    @Autowired
    private OrdemRepository ordemRepository;

    @GetMapping
    public ResponseEntity<OrdemDTO> exibirOrdens(){
        return OrdemDTO.converterOrdens(ordemRepository.findAll());
    }

}
