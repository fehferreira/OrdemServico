package br.com.felipe.pessoal.sistema.ordem_servico.repository;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Long> {
}
