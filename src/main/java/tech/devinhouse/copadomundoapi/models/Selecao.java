package tech.devinhouse.copadomundoapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "selecoes")
public class Selecao {

    @Id
    @Column(length = 3)
    private String sigla;

    private String nome;

    @Column(length = 1)
    private String grupo;

    private LocalDateTime dataDeCadastro;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "selecao")
    private List<Jogador> jogadores;

}
