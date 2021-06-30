package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.service.ObjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObjetoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("Test")
class ObjetoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ObjetoService serviceMock;

    private List<Objeto> listaObjetos;

    private final String url = "/objetos";

    @BeforeEach
    private void beforeEach(){
        this.listaObjetos = new ArrayList<>();
        listaObjetos.add(new Objeto(1L, "Marelli", "IAW 1G7"));
        listaObjetos.add(new Objeto(3L, "Bosch", "ME744"));
        listaObjetos.add(new Objeto(8L, "Ford", "EEC-V"));
    }

    @Test
    void exibirAparelhos_deveriaRetornarListaObjetoDto() throws Exception {
        when(serviceMock.exibirObjetos()).thenReturn(listaObjetos);

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String listaObjetosRetornoJSON = mvcResult.getResponse().getContentAsString();
        String listaExperadaJSON = mapper.writeValueAsString(this.listaObjetos.stream().map(ObjetoDTO::new));

        assertThat(listaObjetosRetornoJSON).isEqualToIgnoringWhitespace(listaExperadaJSON);
    }

}