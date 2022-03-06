package br.edu.ifrn.ifjobs.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrn.ifjobs.exception.VagaNaoCadastradaException;
import br.edu.ifrn.ifjobs.exception.VagaNaoEncontradoException;
import br.edu.ifrn.ifjobs.model.Vaga;
import br.edu.ifrn.ifjobs.repository.VagaRepository;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    public Vaga salvarVaga(Vaga vaga) throws VagaNaoCadastradaException {
        Optional<Vaga> vagaOptional;
        vagaOptional = Optional.ofNullable(vaga);

        Supplier<VagaNaoCadastradaException> excessao;
        excessao = () -> new VagaNaoCadastradaException("Dados inválidos!!");

        return vagaOptional.orElseThrow(excessao);
    }

    public Vaga buscarPorId(int id) throws VagaNaoEncontradoException {
        Optional<Vaga> vagaBuscadaPorId;
        vagaBuscadaPorId = vagaRepository.findById(id);

        Supplier<VagaNaoEncontradoException> excessao;
        excessao = () -> new VagaNaoEncontradoException("Vaga não encontrada!!");

        return vagaBuscadaPorId.orElseThrow(excessao);
    }

    public List<Vaga> buscaTodasVagas() {
        return vagaRepository.findAll();
    }

    public void delete(Vaga vaga) {
        vagaRepository.delete(vaga);
    }

}
