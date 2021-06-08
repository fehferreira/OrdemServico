package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.CadastrarClienteForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente.ClienteInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
            return cliente;
        }catch(IllegalArgumentException exception){
            throw exception;
        }
    }

    public Cliente atualizarCliente(Long id, CadastrarClienteForm form) {
        try{
            Cliente clienteLink;
            clienteLink = atualizarClienteLinkado(id, form);
            return clienteLink;
        }catch (EntityNotFoundException exception){
            throw exception;
        }
    }

    private Cliente atualizarClienteLinkado(Long id, CadastrarClienteForm form) {
        Cliente clienteAtualizado = clienteRepository.getOne(id);
        if(!form.getNome().isEmpty())
            clienteAtualizado.setNome(form.getNome());
        if(!form.getCpf().isEmpty())
            clienteAtualizado.setCpf(form.getCpf());
        if(!form.getEndereco().isEmpty())
            clienteAtualizado.setEndereco(form.getEndereco());
        return clienteAtualizado;
    }

    public Cliente deletarCliente(Long id) {
        try{
            Cliente cliente = clienteRepository.findById(id).get();
            clienteRepository.deleteById(id);
            return cliente;
        }catch (IllegalArgumentException exception){
            throw exception;
        }
    }
}
