package br.com.felipe.pessoal.sistema.ordem_servico.controller.form;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;

public class ObjetoCadastradoForm {

    private String marca;
    private String modelo;

    public ObjetoCadastradoForm(){}

    public ObjetoCadastradoForm(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
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
}
