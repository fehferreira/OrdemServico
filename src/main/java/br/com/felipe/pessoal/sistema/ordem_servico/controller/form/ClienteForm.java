package br.com.felipe.pessoal.sistema.ordem_servico.controller.form;

import com.sun.istack.NotNull;

public class ClienteForm {

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
}
