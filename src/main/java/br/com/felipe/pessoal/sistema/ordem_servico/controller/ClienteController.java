package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @RequestMapping("/clientes")
    @ResponseBody
    public Page<ClienteDto> listarClientes(
            @RequestParam(required = false) String nome,
            @PageableDefault(sort="id", direction = Sort.Direction.ASC) Pageable paginacao){
        if(nome != null){
            return ClienteDto.converter(clienteRepository.findAll(Sort.by(nome)));
        }
        return ClienteDto.converter(clienteRepository.findAll(paginacao));
    }

}
