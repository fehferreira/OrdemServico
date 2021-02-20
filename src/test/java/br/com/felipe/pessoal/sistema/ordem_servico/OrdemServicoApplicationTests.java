package br.com.felipe.pessoal.sistema.ordem_servico;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.transaction.Transactional;
import java.util.Locale;

@SpringBootTest
class OrdemServicoApplicationTests {

	@Autowired
	private ClienteRepository clienteRepository;

	private Faker faker = new Faker(new Locale("pt-BR"));

	@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	}

	@BeforeTestClass
	private void populatingDB(){
		for(int i = 0 ; i < 5 ; i++){
			Cliente cliente = new Cliente(faker.name().toString(),
										  String.valueOf(faker.number().numberBetween(111111111,999999999)),
										  faker.address().toString());
			clienteRepository.save(cliente);
		}
	}

}
