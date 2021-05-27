package br.com.felipe.pessoal.sistema.ordem_servico.controller.form;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdemServicoForm {

    private Long idForm;
    private LocalDateTime dataEntrada = LocalDateTime.now();
    private LocalDateTime dataEntrega;
    private String problemaRelatado;
    private String defeitoEncontrado;
    private String servicoExecutado;
    private Long clienteId;
    private Long aparelhoId;
    private BigDecimal valorServico;
    private Cliente cliente;
    private Objeto aparelho;

    public Long getIdForm() { return idForm; }

    public void setIdForm(Long idForm) { this.idForm = idForm; }

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

    public String getProblemaRelatado() {
        return problemaRelatado;
    }

    public void setProblemaRelatado(String problemaRelatado) {
        this.problemaRelatado = problemaRelatado;
    }

    public String getDefeitoEncontrado() {
        return defeitoEncontrado;
    }

    public void setDefeitoEncontrado(String defeitoEncontrado) {
        this.defeitoEncontrado = defeitoEncontrado;
    }

    public String getServicoExecutado() {
        return servicoExecutado;
    }

    public void setServicoExecutado(String servicoExecutado) {
        this.servicoExecutado = servicoExecutado;
    }

    public Long getClienteId() { return clienteId; }

    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getAparelhoId() { return aparelhoId; }

    public void setAparelhoId(Long aparelhoId) { this.aparelhoId = aparelhoId; }

    public BigDecimal getValorServico() { return valorServico; }

    public void setValorServico(BigDecimal valorServico) { this.valorServico = valorServico; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Objeto getAparelho() { return aparelho; }

    public void setAparelho(Objeto aparelho) { this.aparelho = aparelho; }
}
