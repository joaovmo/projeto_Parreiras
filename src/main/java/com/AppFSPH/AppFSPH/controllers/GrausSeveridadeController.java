package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.GrausSeveridade;
import com.AppFSPH.AppFSPH.services.GrausSeveridadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/graus-severidade")
public class GrausSeveridadeController {

    private final GrausSeveridadeService service;

    @Autowired
    public GrausSeveridadeController(GrausSeveridadeService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os graus de severidade", description = "Retorna uma lista de todos os graus de severidade.")
    @GetMapping
    public ResponseEntity<List<GrausSeveridade>> getAll() {
        List<GrausSeveridade> grausSeveridadeList = service.findAll();
        return ResponseEntity.ok(grausSeveridadeList);
    }

    @Operation(summary = "Buscar grau de severidade por ID", description = "Retorna um grau de severidade específico pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grau de severidade encontrado"),
        @ApiResponse(responseCode = "404", description = "Grau de severidade não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GrausSeveridade> getById(
        @Parameter(description = "ID do grau de severidade a ser buscado") @PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Criar um novo grau de severidade", description = "Adiciona um novo grau de severidade.")
    @ApiResponse(responseCode = "201", description = "Grau de severidade criado com sucesso")
    @PostMapping
    public ResponseEntity<GrausSeveridade> create(@RequestBody GrausSeveridade grausSeveridade) {
        GrausSeveridade createdGrausSeveridade = service.save(grausSeveridade);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrausSeveridade);
    }

    @Operation(summary = "Atualizar um grau de severidade existente", description = "Atualiza os dados de um grau de severidade existente pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grau de severidade atualizado"),
        @ApiResponse(responseCode = "404", description = "Grau de severidade não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GrausSeveridade> update(
        @Parameter(description = "ID do grau de severidade a ser atualizado") @PathVariable int id, 
        @RequestBody GrausSeveridade grausSeveridade) {
        if (service.findById(id).isPresent()) {
            grausSeveridade.setId(id);
            GrausSeveridade updatedGrausSeveridade = service.save(grausSeveridade);
            return ResponseEntity.ok(updatedGrausSeveridade);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar um grau de severidade", description = "Remove um grau de severidade pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Grau de severidade deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Grau de severidade não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID do grau de severidade a ser deletado") @PathVariable int id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
