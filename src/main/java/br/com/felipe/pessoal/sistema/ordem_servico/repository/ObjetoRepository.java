package br.com.felipe.pessoal.sistema.ordem_servico.repository;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Long> {

    Optional<Objeto> findByMarcaAndModelo(String marca, String modelo);

}
