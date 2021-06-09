package br.com.felipe.pessoal.sistema.ordem_servico.service;

import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoAtualizadaForm;
import br.com.felipe.pessoal.sistema.ordem_servico.controller.form.OrdemServicoForm;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Cliente;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.Objeto;
import br.com.felipe.pessoal.sistema.ordem_servico.modelo.OrdemServico;
import br.com.felipe.pessoal.sistema.ordem_servico.repository.OrdemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemRepository ordemRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ObjetoService objetoService;

    public List<OrdemServico> exibirOrdens() {
        return ordemRepository.findAll();
    }


    public OrdemServico cadastrarOrdem(OrdemServicoForm formCadastro) {
        try{
            Cliente cliente = clienteService.detalharCliente(formCadastro.getClienteId());
            Objeto objeto = objetoService.detalharObjeto(formCadastro.getAparelhoId());
            OrdemServico novaOrdem = new OrdemServico(formCadastro.getDataEntrada(),formCadastro.getDataEntrega(),
                                                      cliente,objeto);
            return ordemRepository.save(novaOrdem);
        }catch(IllegalArgumentException exception){
            throw new IllegalArgumentException("Impossível cadastrar nova OS", exception);
        }
    }

    public OrdemServico deletarOrdem(Long id){
        try{
            OrdemServico ordemDeletada = this.detalharOrdem(id);
            ordemRepository.deleteById(id);
            return ordemDeletada;
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("Impossível deletar essa Ordem de Serviço.", exception);
        }
    }

    private OrdemServico detalharOrdem(Long id) {
        try{
            return ordemRepository.findById(id).get();
        }catch (RuntimeException exception){
            throw new EntityNotFoundException("Ordem de serviço nao encontrada no Banco de Dados.");
        }
    }

    public OrdemServico atualizarOrdem(OrdemServicoAtualizadaForm formAtualizado) {
        try {
            Cliente cliente = clienteService.detalharCliente(formAtualizado.getClienteId());
            Objeto aparelho = objetoService.detalharObjeto(formAtualizado.getAparelhoId());
            OrdemServico ordemServicoAtualizada = ordemRepository.getOne(formAtualizado.getIdForm());

            ordemServicoAtualizada.atualizarOrdem(formAtualizado, cliente, aparelho);
            return ordemServicoAtualizada;
        }catch(IllegalArgumentException exception){
            throw new IllegalArgumentException("Impossível atualizar Ordem de Serviço.", exception);
        }
    }
}
