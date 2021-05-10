package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class ObjetoService {

    @Autowired
    private ObjetoRepository objetoRepository;

    public ResponseEntity<ObjetoDTO> cadastrarObjeto(ObjetoCadastradoForm formObjeto, UriComponentsBuilder uriBuilder) {
        Objeto novoObjeto = formObjeto.retornarObjeto();
        try{
            objetoRepository.save(novoObjeto);
        }catch (RuntimeException exception){
            throw new RuntimeException("Impossível salvar este usuário.");
        }
        URI uri =uriBuilder.path("/aparelho/${id}").buildAndExpand(novoObjeto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ObjetoDTO(novoObjeto));
    }

    public ResponseEntity<ObjetoDTO> deletarObjeto(Long id) {
        Optional<Objeto> objeto = objetoRepository.findById(id);
        if(objeto.isPresent()) {
            objetoRepository.delete(objeto.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.notFound().build();
    }
}
