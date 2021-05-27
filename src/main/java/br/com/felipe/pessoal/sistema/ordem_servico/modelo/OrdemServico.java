package br.com.felipe.pessoal.sistema.ordem_servico.modelo;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoAtualizadaForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoForm;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataEntrada = LocalDateTime.now();
    private LocalDateTime dataEntrega;

    private String problemaRelatado;
    private String defeitoEncontrado;
    private String servicoExecutado;

    private BigDecimal valorServico;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Objeto aparelho;

    public OrdemServico(){}

    public OrdemServico(LocalDateTime dataEntrada, LocalDateTime dataEntrega, Cliente cliente, Objeto objeto) {
        this.dataEntrada = dataEntrada;
        this.dataEntrega = dataEntrega;
        this.cliente = cliente;
        this.aparelho = objeto;
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

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Objeto getAparelho() {
        return aparelho;
    }

    public void setAparelho(Objeto aparelho) {
        this.aparelho = aparelho;
    }

    public void atualizarOrdem(OrdemServicoAtualizadaForm formAtualizado) {
        this.dataEntrada = formAtualizado.getDataEntrada();
        this.dataEntrega = formAtualizado.getDataEntrega();
        this.problemaRelatado = formAtualizado.getProblemaRelatado();
        this.defeitoEncontrado = formAtualizado.getDefeitoEncontrado();
        this.servicoExecutado = formAtualizado.getServicoExecutado();
        this.valorServico = formAtualizado.getValorServico();

        this.cliente = formAtualizado.getCliente();
        this.aparelho = formAtualizado.getAparelho();
    }
}
