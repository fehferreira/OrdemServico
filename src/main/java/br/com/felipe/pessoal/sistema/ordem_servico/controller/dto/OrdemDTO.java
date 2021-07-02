package br.com.felipe.pessoal.sistema.ordem_servico.controller.dto;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemDTO {

    private Long id;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataEntrega;
    private Cliente cliente;
    private Objeto aparelho;

    public OrdemDTO(LocalDateTime dataEntrada, LocalDateTime dataEntrega, Cliente cliente, Objeto aparelho) {
        this.dataEntrada = dataEntrada;
        this.dataEntrega = dataEntrega;
        this.cliente = cliente;
        this.aparelho = aparelho;
    }

    public OrdemDTO(OrdemServico ordemServico) {
        this.id = ordemServico.getId();
        this.dataEntrada = ordemServico.getDataEntrada();
        this.dataEntrega = ordemServico.getDataEntrega();
        this.cliente = ordemServico.getCliente();
        this.aparelho = ordemServico.getAparelho();
    }

    public static List<OrdemDTO> converterOrdens(List<OrdemServico> ordens) {
        return ordens.stream().map(OrdemDTO::new).collect(Collectors.toList());
    }

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
