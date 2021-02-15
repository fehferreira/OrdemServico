package br.com.felipe.pessoal.sistema.ordem_servico.controller.dto;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class ClienteDto {

    private String nome;
    private String endereco;

    public ClienteDto(Cliente cliente) {
        this.nome = cliente.getNome();
        this.endereco = cliente.getEndereco();
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public static Page<ClienteDto> converter(Page<Cliente> paginaClientes) {
        return paginaClientes.map(ClienteDto::new);
    }
}