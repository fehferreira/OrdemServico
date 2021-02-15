package br.com.felipe.pessoal.sistema.ordem_servico.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class OrdemServico {

    private Long id;
    private LocalDateTime dataEntrada = LocalDateTime.now();
    private LocalDateTime dataEntrega;
    private BigDecimal valorServico;


    private Cliente cliente;

    private Objeto aparelho;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Objeto getAparelho() {
        return aparelho;
    }

    public void setAparelho(Objeto aparelho) {
        this.aparelho = aparelho;
    }
}
