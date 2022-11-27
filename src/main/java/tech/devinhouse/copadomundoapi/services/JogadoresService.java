package tech.devinhouse.copadomundoapi.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.copadomundoapi.exception.AlreadyRegisteredException;
import tech.devinhouse.copadomundoapi.exception.RegisterNotFoundException;
import tech.devinhouse.copadomundoapi.models.Jogador;
import tech.devinhouse.copadomundoapi.models.Selecao;
import tech.devinhouse.copadomundoapi.repositories.JogadorRepository;
import tech.devinhouse.copadomundoapi.repositories.SelecoesRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JogadoresService {

    @Autowired
    private JogadorRepository repJogador;
    private SelecoesRepository repSelecao;

    public Jogador criar(String sigla,Jogador jogador){
        Optional<Selecao> selecaoOpt = repSelecao.findById(sigla);
        if(selecaoOpt.isEmpty())
            throw new RegisterNotFoundException("Seleção", sigla);
        Selecao selecao = selecaoOpt.get();
        final String nome = jogador.getNome();
        boolean existeComMesmoNomeNoMesmoTime = selecao.getJogadores().stream().anyMatch(p -> p.getNome().equals(nome));
        if (existeComMesmoNomeNoMesmoTime)
            throw new AlreadyRegisteredException("Seleção", sigla);
        jogador.setSelecao(selecao);
        jogador = repJogador.save(jogador);
        return jogador;
    }

    public List<Jogador> consultar(String sigla,String pesquisa){
        Optional<Selecao> selecaoOpt = repSelecao.findById(sigla);
        if(selecaoOpt.isEmpty())
            throw new RegisterNotFoundException("Seleção", sigla);
        Selecao selecao = selecaoOpt.get();
        List<Jogador> jogadores = selecao.getJogadores();
        if (pesquisa == null)
            return jogadores;
        List<Jogador> filtrados = new ArrayList<>();
        for(Jogador jogador : jogadores) {
            if (jogador.getNome().contains(pesquisa)) {
                filtrados.add(jogador);
            }
        }
        return filtrados;
    }

    public void deletar(String sigla, Integer id) {
        Optional<Selecao> selecaoOpt = repSelecao.findById(sigla);
        if(selecaoOpt.isEmpty())
            throw new RegisterNotFoundException("Jogador", sigla);
        Optional<Jogador> buscaOpt = repJogador.findBySelecaoEidJogador(sigla, id);
        if(buscaOpt.isEmpty())
            throw new RegisterNotFoundException("Jogador", id);
        Jogador jogador = buscaOpt.get();
        repJogador.deleteById(jogador.getId());
    }
}
