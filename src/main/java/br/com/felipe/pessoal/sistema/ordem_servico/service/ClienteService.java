package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDetalhadoDto;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ClienteDto;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes(String nome) {
        if(nome != null){
            return clienteRepository.findAllByNome(nome);
        }
        return clienteRepository.findAll();
    }

    public Cliente detalharCliente(Long id) {
        Optional<Cliente> optional = clienteRepository.findById(id);
        if(optional.isEmpty()){
            throw new ClienteInexistenteException("Não foi encontrado este cliente no Banco de Dados!");
        }
        return optional.get();
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        if(clienteRepository.findByNome(cliente.getNome()).isPresent()){
            throw new ClienteExistenteException("Cliente já cadastrado no Banco de Dados!");
        }
        try {
            cliente = clienteRepository.save(cliente);
        }catch(IllegalArgumentException exception){
            throw exception;
        }

        return cliente;
    }
}
