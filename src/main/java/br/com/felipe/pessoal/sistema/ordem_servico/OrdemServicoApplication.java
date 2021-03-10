package br.com.felipe.pessoal.sistema.ordem_servico;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class OrdemServicoApplication {

	public static void main(String[] args) {

		populateDB();

		SpringApplication.run(OrdemServicoApplication.class, args);
	}

	private static void populateDB() {
		Usuario usuario1 = new Usuario("joao_claudio@email.com","123456");
		Usuario usuario2 = new Usuario("felipe_ferreira@email.com","987654");
		Usuario usuario3 = new Usuario("maria_clara@email.com","123789");

		Cliente cliente1 = new Cliente("João Claudio", "123456789-01", "Rua Jean de la huerta 587", usuario1);
		Cliente cliente2 = new Cliente("Felipe Ferreira", "987645321-01", "Rua Paulo de Morais 217", usuario2);
		Cliente cliente3 = new Cliente("Maria Clara", "852963741-56", "Avenida Padre Arlindo Vieira 1089", usuario3);

	}

}
