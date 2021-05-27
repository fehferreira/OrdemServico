package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.OrdemDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoAtualizadaForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ClienteRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.OrdemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemRepository ordemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjetoRepository objetoRepository;

    public Page<OrdemDTO> exibirOrdens(Pageable paginacao) {
        return OrdemDTO.converterOrdens(ordemRepository.findAll(paginacao));
    }


    public ResponseEntity<OrdemDTO> cadastrarOrdem(OrdemServicoForm formCadastro, UriComponentsBuilder uriBuilder) {
        try {
            Cliente cliente = clienteRepository.findById(formCadastro.getClienteId()).get();
            Objeto objeto = objetoRepository.findById(formCadastro.getAparelhoId()).get();
            OrdemServico novaOrdem = new OrdemServico(formCadastro.getDataEntrada(),formCadastro.getDataEntrega(),
                                                      cliente,objeto);
            ordemRepository.save(novaOrdem);
            URI uri =uriBuilder.path("/servicos/${id}").buildAndExpand(novaOrdem.getId()).toUri();
            return ResponseEntity.created(uri).body(new OrdemDTO(novaOrdem));
        }catch (RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<OrdemDTO> deletarOrdem( Long id){
        if(!ordemRepository.findById(id).isPresent())
            return ResponseEntity.notFound().build();
        try{
            ordemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(IllegalArgumentException exception){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<OrdemDTO> atualizarOrdem(OrdemServicoAtualizadaForm formAtualizado) {
        if(!ordemRepository.findById(formAtualizado.getIdForm()).isPresent()){
            return ResponseEntity.notFound().build();
        }
        try{
            OrdemServico ordemServico = ordemRepository.getOne(formAtualizado.getIdForm());
            ordemServico.atualizarOrdem(formAtualizado);
            return ResponseEntity.ok().body(new OrdemDTO(ordemServico));
        }catch (RuntimeException exception){
            return ResponseEntity.badRequest().build();
        }
    }
}
