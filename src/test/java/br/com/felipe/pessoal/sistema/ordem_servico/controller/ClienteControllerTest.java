package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    private final String uriClientes = "/clientes";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void deveriaValidarARequisicaoDeListaDeClientes() throws Exception {
        URI uri = new URI(uriClientes);

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveriaValidarOsParametrosRecebidosViaGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].nome").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].endereco").exists());
    }

    @Test
    public void deveriaRetornarAQuantidadeDeUsuariosCerta() throws Exception {
        List<Cliente> listaClientes = clienteRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes))
                .andExpect(MockMvcResultMatchers.jsonPath("numberOfElements", Matchers.is(listaClientes.size())));
    }

    @Test
    public void deveriaRetornarApenasUmaListaComUsuariosDeMesmoNome() throws Exception {
        Page<Cliente> listaClientesComFiltroPeloNome = clienteRepository.findAllByNome("Felipe Ferreira", PageRequest.of(0, 10, Sort.by("id").ascending()));

        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes)
                .param("nome", "Felipe Ferreira"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("totalElements",
                            Matchers.is((int) listaClientesComFiltroPeloNome.getTotalElements())))
                    .andExpect(MockMvcResultMatchers.jsonPath("content[0].nome",
                            Matchers.is(listaClientesComFiltroPeloNome.getContent().get(0).getNome())));
    }

    @Test
    public void retornoDeListaVaziaPorReceberClienteNaoExistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes)
                .param("nome", "Felipe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content").isEmpty());
    }

    @Test
    public void testandoRequisicaoDeDadosClienteDetalhado() throws Exception {
        Cliente clienteDetalhado = clienteRepository.findById(1L).get();

        mockMvc.perform(MockMvcRequestBuilders.get(uriClientes)
                .param("id","1L"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].id", Matchers.is(clienteDetalhado.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].nome", Matchers.is(clienteDetalhado.getNome())));

    }

}