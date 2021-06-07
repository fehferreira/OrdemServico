package br.com.felipe.pessoal.sistema.ordem_servico.exceptions.cliente;

public class ClienteInexistenteException extends RuntimeException {
    public ClienteInexistenteException(String message) {
        super(message);
    }
}
