package tech.devinhouse.copadomundoapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.devinhouse.copadomundoapi.models.Jogador;

import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    @Query("SELECT j FROM Jogador j WHERE j.selecao.sigla = :sigla AND j.id = :id")
    Optional<Jogador> findBySelecaoEidJogador(@Param("sigla") String sigla, @Param("id") Integer idJogador);

    @Query("SELECT j FROM Jogador j WHERE j.selecao.nome = :nome")
    Optional<Jogador> findJogadorByNome(String nome);

}
