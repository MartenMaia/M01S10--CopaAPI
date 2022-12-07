package tech.devinhouse.copadomundoapi.dto;

import lombok.Data;
import tech.devinhouse.copadomundoapi.models.Papel;

import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioResponse {

    private Integer id;

    private String email;

    private LocalDate dataNascimento;

    private List<Papel> papeis;

}
