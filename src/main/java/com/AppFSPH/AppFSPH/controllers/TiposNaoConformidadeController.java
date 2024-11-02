package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.TiposNaoConformidade;
import com.AppFSPH.AppFSPH.services.TiposNaoConformidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-nao-conformidade")
public class TiposNaoConformidadeController {

    private final TiposNaoConformidadeService service;

    @Autowired
    public TiposNaoConformidadeController(TiposNaoConformidadeService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os tipos de não conformidade", description = "Retorna uma lista de todos os tipos de não conformidade.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    @GetMapping
    public ResponseEntity<List<TiposNaoConformidade>> getAll() {
        List<TiposNaoConformidade> tiposNaoConformidadeList = service.findAll();
        return ResponseEntity.ok(tiposNaoConformidadeList);
    }

    @Operation(summary = "Buscar tipo de não conformidade por ID", description = "Retorna um tipo de não conformidade específico pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de não conformidade encontrado"),
        @ApiResponse(responseCode = "404", description = "Tipo de não conformidade não encontrado")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    @GetMapping("/{id}")
    public ResponseEntity<TiposNaoConformidade> getById(
        @Parameter(description = "ID do tipo de não conformidade a ser buscado") @PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Criar um novo tipo de não conformidade", description = "Adiciona um novo tipo de não conformidade.")
    @ApiResponse(responseCode = "201", description = "Tipo de não conformidade criado com sucesso")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    @PostMapping
    public ResponseEntity<TiposNaoConformidade> create(@RequestBody TiposNaoConformidade tiposNaoConformidade) {
        TiposNaoConformidade createdTipoNaoConformidade = service.save(tiposNaoConformidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTipoNaoConformidade);
    }

    @Operation(summary = "Atualizar um tipo de não conformidade existente", description = "Atualiza os dados de um tipo de não conformidade existente pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de não conformidade atualizado"),
        @ApiResponse(responseCode = "404", description = "Tipo de não conformidade não encontrado")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    @PutMapping("/{id}")
    public ResponseEntity<TiposNaoConformidade> update(
        @Parameter(description = "ID do tipo de não conformidade a ser atualizado") @PathVariable int id, 
        @RequestBody TiposNaoConformidade tiposNaoConformidade) {
        if (service.findById(id).isPresent()) {
            tiposNaoConformidade.setId(id);
            TiposNaoConformidade updatedTipoNaoConformidade = service.save(tiposNaoConformidade);
            return ResponseEntity.ok(updatedTipoNaoConformidade);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar um tipo de não conformidade", description = "Remove um tipo de não conformidade pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tipo de não conformidade deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tipo de não conformidade não encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID do tipo de não conformidade a ser deletado") @PathVariable int id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
