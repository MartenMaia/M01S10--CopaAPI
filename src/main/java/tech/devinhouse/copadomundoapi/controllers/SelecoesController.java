package tech.devinhouse.copadomundoapi.controllers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.copadomundoapi.dto.SelecaoRequest;
import tech.devinhouse.copadomundoapi.dto.SelecaoResponse;
import tech.devinhouse.copadomundoapi.models.Selecao;
import tech.devinhouse.copadomundoapi.services.SelecoesService;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1.0.0/selecoes")
@AllArgsConstructor
public class SelecoesController {

    @Autowired
    private SelecoesService service;

    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<SelecaoResponse> post(@RequestBody @Valid SelecaoRequest request){
        Selecao selecao = mapper.map(request,Selecao.class);
        selecao = service.criar(selecao);
        SelecaoResponse resp = mapper.map(selecao,SelecaoResponse.class);
        return ResponseEntity.created(URI.create(resp.getSigla())).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<SelecaoResponse>> get(){
        List<Selecao> selecoes = service.consultar();
        List<SelecaoResponse> resp = new ArrayList<>();
        for (Selecao selecao : selecoes){
            SelecaoResponse response = mapper.map(selecao, SelecaoResponse.class);
            resp.add(response);
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping(path = "{sigla}")
    public ResponseEntity<SelecaoResponse> get(@PathVariable("sigla") String sigla){
        Selecao selecao = service.consultar(sigla);
        SelecaoResponse resp = mapper.map(selecao, SelecaoResponse.class);
        return ResponseEntity.ok(resp);
    }

    @PutMapping(path = "{sigla}")
    public ResponseEntity<SelecaoResponse> put(@PathVariable("sigla") String sigla, @RequestBody @Valid SelecaoRequest request){
        Selecao selecao = mapper.map(request, Selecao.class);
        selecao = service.atualizar(sigla,selecao);
        SelecaoResponse resp = mapper.map(selecao, SelecaoResponse.class);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping(path = "{sigla}")
    public ResponseEntity<SelecaoResponse> delete(@PathVariable("sigla") String sigla){
        service.excluir(sigla);
        return ResponseEntity.noContent().build();
    }

}
