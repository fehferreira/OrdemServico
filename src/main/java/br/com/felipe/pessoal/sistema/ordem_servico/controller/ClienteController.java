package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.CadastrarClienteForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ClienteForm;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @ResponseBody
    @GetMapping("/{nome}")
    public Page<ClienteDto> listarClientes(
            @RequestParam(required = false) @PathVariable String nome,
            @PageableDefault(sort="id", direction = Sort.Direction.ASC) Pageable paginacao){
        if(nome != null){
            return ClienteDto.converter(clienteRepository.findAll(Sort.by(nome)));
        }
        return ClienteDto.converter(clienteRepository.findAll(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> detalharCliente(@PathVariable @RequestParam Long id){
        ClienteDto cliente;
        try{
            cliente = new ClienteDto(clienteRepository.findById(id).get());
        }catch(IllegalArgumentException exception){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> cadastrarCliente(@RequestBody CadastrarClienteForm form, UriComponentsBuilder uri){
        return null;
    }


    @PutMapping
    public ResponseEntity<ClienteDto> atualizarCliente(@RequestBody ClienteForm form ){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@RequestParam @PathVariable Long id){
        return null;
    }
}
