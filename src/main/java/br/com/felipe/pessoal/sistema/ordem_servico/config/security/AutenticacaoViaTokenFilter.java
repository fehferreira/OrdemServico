package br.com.felipe.pessoal.sistema.ordem_servico.config.security;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Usuario;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.UsuarioRepository;
import org.hibernate.annotations.Filter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        System.out.println(token);
        if(tokenService.isTokenValido(token)){
            autenticarCliente(token);
        }
        filterChain.doFilter(request,response);
    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Usuario usuarioLogado = usuarioRepository.findById(idUsuario);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer "))
            return null;
        return token.substring(7,token.length());
    }
}
