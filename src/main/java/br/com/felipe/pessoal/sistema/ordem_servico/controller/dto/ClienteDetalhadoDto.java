package br.com.felipe.pessoal.sistema.ordem_servico.controller.dto;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import org.springframework.data.domain.Page;

import java.util.List;

public class ClienteDetalhadoDto {

    private Long id;
    private String nome;
    private String endereco;
    private List<OrdemServico> servicos;

    public ClienteDetalhadoDto(Cliente cliente) {
        this.nome = cliente.getNome();
        this.endereco = cliente.getEndereco();
        this.servicos = cliente.getServicos();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Page<ClienteDetalhadoDto> converter(Page<Cliente> paginaClientes) {
        return paginaClientes.map(ClienteDetalhadoDto::new);
    }

    public static Page<ClienteDetalhadoDto> converter(List<Cliente> listaClientes) {

        return null;
    }

}