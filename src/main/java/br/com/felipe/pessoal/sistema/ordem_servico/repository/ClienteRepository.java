package br.com.felipe.pessoal.sistema.ordem_servico.repository;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Page<Cliente> findByNome(String nome, Pageable paginacao);
}
