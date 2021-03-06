package br.com.felipe.pessoal.sistema.ordem_servico.controller.form;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import com.sun.istack.NotNull;

public class CadastrarClienteForm {

    @NotNull
    private String nome;
    @NotNull
    private String cpf;
    @NotNull
    private String endereco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Cliente retornarCliente() {
        return new Cliente(this.nome,this.cpf,this.endereco);
    }

    public Cliente atualizarCliente(Long id, ClienteRepository clienteRepository) {
        Cliente clienteAtualizado = clienteRepository.getOne(id);
        if(!this.nome.isEmpty())clienteAtualizado.setNome(this.nome);
        if(!this.cpf.isEmpty())clienteAtualizado.setCpf(this.cpf);
        if(!this.endereco.isEmpty())clienteAtualizado.setEndereco(this.endereco);
        return clienteAtualizado;
    }
}
