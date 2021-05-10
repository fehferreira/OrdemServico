package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aparelho")
public class ObjetoController {

    @Autowired
    private ObjetoRepository objetoRepository;

    public Page<ObjetoDTO> exibirAparelhos(@PageableDefault Pageable paginacao){
        return ObjetoDTO.converterObjetos(objetoRepository.findAll(paginacao));
    }


}
