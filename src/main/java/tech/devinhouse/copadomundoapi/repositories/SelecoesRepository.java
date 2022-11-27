package tech.devinhouse.copadomundoapi.repositories;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.copadomundoapi.models.Selecao;

@Repository
public interface SelecoesRepository extends JpaRepository<Selecao,String> {
}
