package br.com.felipe.pessoal.sistema.ordem_servico.controller.form;

import java.time.LocalDateTime;

public class OrdemServicoForm {

    private LocalDateTime dataEntrada = LocalDateTime.now();
    private LocalDateTime dataEntrega;
    private String problemaRelatado;
    private String defeitoEncontrado;
    private String servicoExecutado;
    private Long clienteId;
    private Long aparelhoId;

    public OrdemServicoForm(Long clienteId, Long aparelhoId) {
        this.clienteId = clienteId;
        this.aparelhoId = aparelhoId;
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
}
