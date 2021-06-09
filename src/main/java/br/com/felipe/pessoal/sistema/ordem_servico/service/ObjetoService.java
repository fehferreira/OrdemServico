package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoAtualizadoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.ObjetoCadastradoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.NenhumObjetoCadastradoException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoExistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.exceptions.ObjetoInexistenteException;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.ObjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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
        if(objetoRepository.findByMarcaAndModelo(novoObjeto.getMarca(), novoObjeto.getModelo()).isPresent()){
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
        if(optional.isEmpty()){
            throw new ObjetoInexistenteException("Este objeto não existe no Banco de Dados.");
        }
        return formAtualizado.atualizar(formAtualizado.getId(), objetoRepository);
    }

    public List<Objeto> exibirObjetos() {
        List<Objeto> objetos = objetoRepository.findAll();
        if(objetos.isEmpty()){
            throw new NenhumObjetoCadastradoException("Nenhum objeto encontrado no Banco de Dados!");
        }
        return objetos;
    }

    public Objeto detalharObjeto(Long objetoId) {
        try{
            return objetoRepository.findById(objetoId).get();
        }catch (Exception exception){
            throw new EntityNotFoundException("Este objeto não existe no Banco de Dados.");
        }
    }
}
