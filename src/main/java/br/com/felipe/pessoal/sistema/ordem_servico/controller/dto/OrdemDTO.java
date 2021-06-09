package br.com.felipe.pessoal.sistema.ordem_servico.controller.dto;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class OrdemDTO {

    private Long id;
    private LocalDateTime dataEntrada = LocalDateTime.now();
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

    public static Page<OrdemDTO> converterOrdens(Page<OrdemServico> ordens) {
        return ordens.map(OrdemDTO::new);
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
