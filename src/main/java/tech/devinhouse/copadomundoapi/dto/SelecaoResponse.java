package tech.devinhouse.copadomundoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SelecaoResponse {

    private String sigla;
    private String nome;
    private String grupo;
    private LocalDateTime dataDeCadastro;

}
