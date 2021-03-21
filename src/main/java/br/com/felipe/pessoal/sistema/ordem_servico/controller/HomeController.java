package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Usuario;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjetoRepository objetoRepository;

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        try {
            populateSpy();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "HOME PAGE";
    }

    private void populateSpy() {
        List<Usuario> usuarios = new ArrayList<>();
        List<Objeto> objetos = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();

        usuarios.add(new Usuario("joao_claudio@email.com","$2y$12$XeUaWj5g76ra0w.nPbBgG.zEhf1MNKnvP7QU//tZckoRC8TNA7rVK"));
        usuarios.add(new Usuario("felipe_ferreira@email.com","$2y$12$yqDTn4mUDRoFxLWgDvI7ZOaLyODqSaD7FBPjfROJNoL67NeHT1tFK"));
        usuarios.add(new Usuario("maria_clara@email.com","$2y$12$iv5GJxM1LqVqzVqRD3dO1e1bnMa8skiMjHPY7IXB1Elqqh3llWdLi"));

        clientes.add(new Cliente("João Claudio", "123456789-01", "Rua Jean de la huerta 587", usuarios.get(0)));
        clientes.add(new Cliente("Felipe Ferreira", "987645321-01", "Rua Paulo de Morais 217", usuarios.get(1)));
        clientes.add(new Cliente("Maria Clara", "852963741-56", "Avenida Padre Arlindo Vieira 1089", usuarios.get(2)));

        objetos.add(new Objeto("Marelli", "IAW 1G7"));
        objetos.add(new Objeto("Bosch", "ME 7.9.9"));
        objetos.add(new Objeto("Ford", "EEC-V"));

        usuarioRepository.saveAll(usuarios);
        clienteRepository.saveAll(clientes);
        objetoRepository.saveAll(objetos);
    }

}
