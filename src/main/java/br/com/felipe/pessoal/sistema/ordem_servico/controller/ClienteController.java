package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDetalhadoDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.CadastrarClienteForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @ResponseBody
    @GetMapping
    public List<ClienteDto> listarClientes(@RequestParam(required = false) String nome){
        return clienteService.listarClientes(nome).stream().map(ClienteDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDetalhadoDto> detalharCliente(@PathVariable Long id){
        ClienteDetalhadoDto cliente;
        try{
            cliente = new ClienteDetalhadoDto(clienteRepository.findById(id).get());
        }catch(IllegalArgumentException exception){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> cadastrarCliente(@RequestBody CadastrarClienteForm form, UriComponentsBuilder uriBuilder){
        Cliente cliente = form.retornarCliente();
        clienteRepository.save(cliente);

        URI uri =uriBuilder.path("/clientes/${id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteDto(cliente));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable Long id , @RequestBody CadastrarClienteForm form ){
        Optional<Cliente> clienteBanco = clienteRepository.findById(id);
        if(clienteBanco.isPresent()){
            Cliente clienteAtual = form.atualizarCliente(id,clienteRepository);
            return ResponseEntity.ok(new ClienteDto(clienteAtual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id){
        try{
            clienteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
