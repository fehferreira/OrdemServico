package br.com.felipe.pessoal.sistema.ordem_servico.controller.dto;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import org.springframework.data.domain.Page;

public class ObjetoDTO {

    private String marca;
    private String modelo;

    public ObjetoDTO(Objeto objeto){
        this.marca = objeto.getMarca();
        this.modelo = objeto.getModelo();
    }

    public static Page<ObjetoDTO> converterObjetos(Page<Objeto> objetos) {
        return objetos.map(ObjetoDTO::new);
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
}
