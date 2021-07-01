package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.OrdemDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import br.com.felipe.pessoal.sistema.ordem_servico.service.OrdemServicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(OrdemServicoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class OrdemServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrdemServicoService serviceMock;

    private List<OrdemServico> listaOrdemServico;

    private final String url = "/servicos";

    @BeforeEach()
    public void beforeEach(){
        Cliente cliente1 = new Cliente("Felipe", "12345678901", "Rua dos botucatus 57");
        Cliente cliente2 = new Cliente("Joana", "78965432101", "Alameda dos japorus 973");
        Cliente cliente3 = new Cliente("Mario", "78945698401", "Avenida das rainhas 5");

        cliente1.setId(1L);
        cliente2.setId(3L);
        cliente3.setId(5L);

        Objeto objeto1 = new Objeto(1L, "Marelli", "IAW 1G7");
        Objeto objeto2 = new Objeto(3L, "Bosch", "ME744");

        this.listaOrdemServico = new ArrayList<>();

        this.listaOrdemServico.add(new OrdemServico(LocalDateTime.now().plusDays(2L), LocalDateTime.now().plusDays(4L), cliente1, objeto2));
        this.listaOrdemServico.add(new OrdemServico(LocalDateTime.now().plusDays(4L), LocalDateTime.now().plusDays(5L), cliente2, objeto2));
        this.listaOrdemServico.add(new OrdemServico(LocalDateTime.now().plusDays(3L), LocalDateTime.now().plusDays(8L), cliente1, objeto1));
    }

    @Test
    void exibirOrdens_deveriaRetornarListaOrdemDto() throws Exception {
        when(serviceMock.exibirOrdens()).thenReturn(this.listaOrdemServico);

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String retornoListaOrdensJSON = mvcResult.getResponse().getContentAsString();
        String listaOrdensExperadaJSON = mapper.writeValueAsString(this.listaOrdemServico.stream().map(OrdemDTO::new));

        assertThat(retornoListaOrdensJSON).isEqualToIgnoringWhitespace(listaOrdensExperadaJSON);
    }

}