package tech.devinhouse.copadomundoapi.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.devinhouse.copadomundoapi.exception.AlreadyRegisteredException;
import tech.devinhouse.copadomundoapi.exception.RegisterNotFoundException;
import tech.devinhouse.copadomundoapi.models.Selecao;
import tech.devinhouse.copadomundoapi.repositories.SelecoesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SelecoesService {

    @Autowired
    private SelecoesRepository repo;

    public Selecao criar(Selecao selecao){
        if(repo.existsById(selecao.getSigla()))
            throw new AlreadyRegisteredException("Selecao", selecao.getSigla());
        selecao.setDataDeCadastro(LocalDateTime.now());
        repo.save(selecao);
        return selecao;
    }

    public List<Selecao> consultar(){
        return  repo.findAll();
    }

    public Selecao consultar(String sigla){
        Optional<Selecao> consulta = repo.findById(sigla);
        if (consulta.isEmpty())
            throw new RegisterNotFoundException("Seleções", sigla);
        return consulta.get();
    }

    public Selecao atualizar(String sigla, Selecao selecao){
        Optional<Selecao> busca = repo.findById(sigla);
        if (busca.isEmpty())
            throw new RegisterNotFoundException(sigla, "Selecoes");
        Selecao selecaoBD = busca.get();
        selecaoBD.setSigla(selecao.getSigla());
        selecaoBD.setNome(selecao.getNome());
        selecaoBD.setGrupo(selecao.getGrupo());
        selecaoBD.setDataDeCadastro(selecao.getDataDeCadastro());
        selecao = repo.save(selecaoBD);
        return selecao;
    }

    public void excluir(String sigla) {
        Optional<Selecao> busca = repo.findById(sigla);
        if(busca.isEmpty())
            throw new RegisterNotFoundException(sigla,"Selecoes");
        repo.deleteById(sigla);
    }
}
