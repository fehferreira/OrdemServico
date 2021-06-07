package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDetalhadoDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.CadastrarClienteForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
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
            cliente = new ClienteDetalhadoDto(clienteService.detalharCliente(id));
        }catch(ClienteInexistenteException exception){
            return new ResponseEntity(exception, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(cliente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> cadastrarCliente(@RequestBody CadastrarClienteForm form, UriComponentsBuilder uriBuilder){
        Cliente cliente = form.retornarCliente();

        try {
            cliente = clienteService.cadastrarCliente(cliente);
        }catch(ClienteExistenteException exception){
            return new ResponseEntity(exception, HttpStatus.FOUND);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }

        URI uri =uriBuilder.path("/clientes/${id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteDto(cliente));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable Long id , @RequestBody CadastrarClienteForm form ){
        Cliente clienteAtualizado;

        try {
            clienteAtualizado = clienteService.atualizarCliente(id, form);
        }catch (EntityNotFoundException exception){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity(clienteAtualizado,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteDto> deletarCliente(@PathVariable Long id){
        try{
            return ResponseEntity.ok(new ClienteDto(clienteService.deletarCliente(id)));
        }catch(EntityNotFoundException exception){
            return new ResponseEntity(exception, HttpStatus.NOT_FOUND);
        }
    }

}
