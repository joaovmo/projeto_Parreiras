package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.Departamento;
import com.AppFSPH.AppFSPH.services.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @Operation(summary = "Listar todos os departamentos", description = "Retorna uma lista de todos os departamentos.")
    @GetMapping
    public List<Departamento> getAll() {
        return departamentoService.findAll();
    }

    @Operation(summary = "Buscar departamento por ID", description = "Retorna um departamento específico pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
        @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getById(
        @Parameter(description = "ID do departamento a ser buscado") @PathVariable Long id) {
        Departamento departamento = departamentoService.findById(id);
        if (departamento != null) {
            return ResponseEntity.ok(departamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Criar um novo departamento", description = "Adiciona um novo departamento.")
    @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso")
    @PostMapping
    public Departamento create(@RequestBody Departamento departamento) {
        return departamentoService.save(departamento);
    }

    @Operation(summary = "Atualizar um departamento existente", description = "Atualiza os dados de um departamento existente pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Departamento atualizado"),
        @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Departamento> update(
        @Parameter(description = "ID do departamento a ser atualizado") @PathVariable Long id, 
        @RequestBody Departamento departamento) {
        if (departamentoService.findById(id) != null) {
            departamento.setId(id); // Supondo que o ID está na classe Departamento
            return ResponseEntity.ok(departamentoService.save(departamento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deletar um departamento", description = "Remove um departamento pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Departamento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID do departamento a ser deletado") @PathVariable Long id) {
        if (departamentoService.findById(id) != null) {
            departamentoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
