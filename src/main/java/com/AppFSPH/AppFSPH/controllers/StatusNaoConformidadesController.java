package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.StatusNaoConformidades;
import com.AppFSPH.AppFSPH.services.StatusNaoConformidadesService;
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
@RequestMapping("/api/status-nao-conformidades")
public class StatusNaoConformidadesController {

    private final StatusNaoConformidadesService service;

    @Autowired
    public StatusNaoConformidadesController(StatusNaoConformidadesService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os status de não conformidades", description = "Retorna uma lista de todos os status de não conformidades.")
    @GetMapping
    public ResponseEntity<List<StatusNaoConformidades>> getAll() {
        List<StatusNaoConformidades> statusList = service.findAll();
        return ResponseEntity.ok(statusList);
    }

    @Operation(summary = "Buscar status de não conformidade por ID", description = "Retorna um status de não conformidade específico pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status de não conformidade encontrado"),
        @ApiResponse(responseCode = "404", description = "Status de não conformidade não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StatusNaoConformidades> getById(
        @Parameter(description = "ID do status de não conformidade a ser buscado") @PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Criar um novo status de não conformidade", description = "Adiciona um novo status de não conformidade.")
    @ApiResponse(responseCode = "201", description = "Status de não conformidade criado com sucesso")
    @PostMapping
    public ResponseEntity<StatusNaoConformidades> create(@RequestBody StatusNaoConformidades statusNaoConformidade) {
        StatusNaoConformidades createdStatus = service.save(statusNaoConformidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    @Operation(summary = "Atualizar um status de não conformidade existente", description = "Atualiza os dados de um status de não conformidade existente pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status de não conformidade atualizado"),
        @ApiResponse(responseCode = "404", description = "Status de não conformidade não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StatusNaoConformidades> update(
        @Parameter(description = "ID do status de não conformidade a ser atualizado") @PathVariable int id, 
        @RequestBody StatusNaoConformidades statusNaoConformidade) {
        if (service.findById(id).isPresent()) {
            statusNaoConformidade.setId(id);
            StatusNaoConformidades updatedStatus = service.save(statusNaoConformidade);
            return ResponseEntity.ok(updatedStatus);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar um status de não conformidade", description = "Remove um status de não conformidade pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Status de não conformidade deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Status de não conformidade não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID do status de não conformidade a ser deletado") @PathVariable int id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
