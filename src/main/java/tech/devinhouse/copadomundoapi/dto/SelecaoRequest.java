package tech.devinhouse.copadomundoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
public class SelecaoRequest {

    private String sigla;
    private String nome;
    private String grupo;

}
