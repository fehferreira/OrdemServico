package br.com.felipe.pessoal.sistema.ordem_servico.controller.form;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;

public class ObjetoAtualizadoForm {

    private Long id;
    private String marca;
    private String modelo;

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

    public Objeto retornarObjeto() {
        return new Objeto(this.marca, this.modelo);
    }

    public Objeto atualizar(Long id, ObjetoRepository objetoRepository) {
        Objeto objetoAtual = objetoRepository.getOne(id);
        objetoAtual.setMarca(this.marca);
        objetoAtual.setModelo(this.modelo);
        return objetoAtual;
    }
}
