package br.com.felipe.pessoal.sistema.ordem_servico.config.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String gerarToken(Authentication authentication) {
        return Jwts.builder()
                .setIssuer("API do ")

    }
}
