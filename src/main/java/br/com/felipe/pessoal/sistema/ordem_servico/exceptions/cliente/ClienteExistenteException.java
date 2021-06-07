package br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente;

public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException(String message) {
        super(message);
    }
}
