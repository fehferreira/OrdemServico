package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @InjectMocks
    private ClienteService clienteService;

    private UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    

}