package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/aparelho")
public class ObjetoController {

    @Autowired
    private ObjetoRepository objetoRepository;

    @GetMapping
    public Page<ObjetoDTO> exibirAparelhos(@PageableDefault Pageable paginacao){
        return ObjetoDTO.converterObjetos(objetoRepository.findAll(paginacao));
    }

    @PostMapping
    public ResponseEntity<ObjetoDTO> cadastrarObjeto(@RequestBody ObjetoCadastradoForm form, UriComponentsBuilder uri){
        
    }


}
