package br.com.felipe.pessoal.sistema.ordem_servico.repository;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String email);

}
