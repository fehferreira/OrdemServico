package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.dto.ObjetoDTO;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoAtualizadoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoInexistenteException;
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

    private ObjetoRepository objetoRepository;

    @Autowired
    public ObjetoService(ObjetoRepository objetoRepository) {
        this.objetoRepository = objetoRepository;
    }

    public Objeto cadastrarObjeto(ObjetoCadastradoForm formObjeto) {
        Objeto novoObjeto = formObjeto.retornarObjeto();
        if(objetoRepository.findByMarcaAndModelo(novoObjeto).isPresent()){
            throw new ObjetoExistenteException("Objeto já existe no Banco de Dados.");
        }
        try{
            novoObjeto = objetoRepository.save(novoObjeto);
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("Impossível salvar este usuário.",exception);
        }
        return novoObjeto;
    }

    public Objeto deletarObjeto(Long id) {
        Optional<Objeto> objeto = objetoRepository.findById(id);
        if(objeto.isEmpty()) {
            throw new ObjetoInexistenteException("Este objeto não existe no Banco de Dados.");
        }
        Objeto objetoDeletado = objeto.get();
        objetoRepository.delete(objetoDeletado);
        return objetoDeletado;
    }

    public Objeto alterarObjeto(ObjetoAtualizadoForm formAtualizado) {
        Optional<Objeto> optional = objetoRepository.findById(formAtualizado.getId());
        if(optional.isPresent()){
            throw new ObjetoInexistenteException("Este objeto não existe no Banco de Dados.");
        }
        return formAtualizado.atualizar(formAtualizado.getId(), objetoRepository);
    }
}
