package tech.devinhouse.copadomundoapi.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.copadomundoapi.dto.JogadorRequest;
import tech.devinhouse.copadomundoapi.dto.JogadorResponse;
import tech.devinhouse.copadomundoapi.dto.SelecaoResponse;
import tech.devinhouse.copadomundoapi.models.Jogador;
import tech.devinhouse.copadomundoapi.models.Selecao;
import tech.devinhouse.copadomundoapi.services.JogadoresService;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0.0/selecoes/{sigla}/jogadores")
@AllArgsConstructor
public class JogadoresController {

    private JogadoresService service;
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<JogadorResponse> criar(@PathVariable("sigla") String sigla, @RequestBody @Valid JogadorRequest request){
        Jogador jogador = mapper.map(request, Jogador.class);
        jogador = service.criar(sigla,jogador);
        JogadorResponse resp = mapper.map(jogador, JogadorResponse.class);
        return ResponseEntity.created(URI.create(resp.getId().toString())).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<JogadorResponse>> get(@PathVariable("sigla") String sigla, @RequestParam(value = "nome", required = false) String nomePesquisa){
        List<Jogador> jogadores = service.consultar(sigla , nomePesquisa);
        List<JogadorResponse> resp = new ArrayList<>();
        for( Jogador j : jogadores){
            resp.add(mapper.map(j,JogadorResponse.class));
        }
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("sigla") String sigla, @PathVariable("id") Integer id){
        service.deletar(sigla,id);
        return ResponseEntity.noContent().build();
    }



}
