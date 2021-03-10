package br.com.felipe.pessoal.sistema.ordem_servico.modelo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modelo;

    @OneToMany
    private List<OrdemServico> servicos;

    public Objeto(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public List<OrdemServico> getServicos() {
        return servicos;
    }

    public void setServicos(List<OrdemServico> servicos) {
        this.servicos = servicos;
    }
}
