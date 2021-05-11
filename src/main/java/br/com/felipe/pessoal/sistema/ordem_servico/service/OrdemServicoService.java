package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.OrdemDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.OrdemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemRepository ordemRepository;

    public Page<OrdemDTO> exibirOrdens(Pageable paginacao) {
        return OrdemDTO.converterOrdens(ordemRepository.findAll(paginacao));
    }
}
