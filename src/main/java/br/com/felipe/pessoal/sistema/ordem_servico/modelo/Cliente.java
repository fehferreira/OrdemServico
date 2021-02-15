package br.com.felipe.pessoal.sistema.ordem_servico.modelo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String endereco;

    @OneToMany
    private List<OrdemServico> servicos;

    public Cliente(){

    }

    public Cliente(String nome, String cpf, String endereco){
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<OrdemServico> getServicos() {
        return servicos;
    }

    public void setServicos(List<OrdemServico> servicos) {
        this.servicos = servicos;
    }
}
