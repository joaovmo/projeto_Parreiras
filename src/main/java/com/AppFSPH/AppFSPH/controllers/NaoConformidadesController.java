package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.NaoConformidades;
import com.AppFSPH.AppFSPH.services.NaoConformidadesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/nao-conformidades")
public class NaoConformidadesController {

    private final NaoConformidadesService service;

    @Autowired
    public NaoConformidadesController(NaoConformidadesService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as não conformidades", description = "Retorna uma lista de todas as não conformidades.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE') or hasRole('CONTROLE_QUALIDADE')")
    @GetMapping
    public ResponseEntity<List<NaoConformidades>> getAll() {
        List<NaoConformidades> naoConformidadesList = service.findAll();
        return ResponseEntity.ok(naoConformidadesList);
    }

    @Operation(summary = "Buscar não conformidade por ID", description = "Retorna uma não conformidade específica pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Não conformidade encontrada"),
        @ApiResponse(responseCode = "404", description = "Não conformidade não encontrada")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE') or hasRole('CONTROLE_QUALIDADE')")
    @GetMapping("/{id}")
    public ResponseEntity<NaoConformidades> getById(
        @Parameter(description = "ID da não conformidade a ser buscada") @PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Criar uma nova não conformidade", description = "Adiciona uma nova não conformidade.")
    @ApiResponse(responseCode = "201", description = "Não conformidade criada com sucesso")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE') or hasRole('CONTROLE_QUALIDADE') or hasRole('COMUM')")
    public ResponseEntity<NaoConformidades> create(@RequestBody NaoConformidades naoConformidade,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Define o usuário criador com base no usuário autenticado
            String usuarioNome = userDetails.getUsername(); // Obtém o nome do usuário autenticado
            naoConformidade.setUsuarioNome(usuarioNome);
            
            // Definindo data e hora de criação e alteração
            naoConformidade.setDataHoraCriacao(LocalDateTime.now());
            naoConformidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
            
            // Salva a nova não conformidade no serviço
            NaoConformidades createdNaoConformidade = service.save(naoConformidade);
            
            // Retorna resposta com status 201 (CREATED) e o objeto criado
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNaoConformidade);
        } catch (Exception e) {
            // Em caso de erro, retorna um status de erro 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualizar uma não conformidade existente", description = "Atualiza os dados de uma não conformidade existente pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Não conformidade atualizada"),
        @ApiResponse(responseCode = "404", description = "Não conformidade não encontrada")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    public ResponseEntity<NaoConformidades> update(
        @Parameter(description = "ID da não conformidade a ser atualizada") @PathVariable int id, 
        @RequestBody NaoConformidades naoConformidade) {
        if (service.findById(id).isPresent()) {
            naoConformidade.setId(id);
            naoConformidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
            NaoConformidades updatedNaoConformidade = service.save(naoConformidade);
            return ResponseEntity.ok(updatedNaoConformidade);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar uma não conformidade", description = "Remove uma não conformidade pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Não conformidade deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Não conformidade não encontrada")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID da não conformidade a ser deletada") @PathVariable int id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Listar não conformidades por ID de departamento", description = "Retorna uma lista de não conformidades associadas a um departamento específico.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    @GetMapping("/departamento/{departamentoId}")
    public ResponseEntity<List<NaoConformidades>> getByDepartamentoId(
        @Parameter(description = "ID do departamento a ser buscado") @PathVariable int departamentoId) {
        List<NaoConformidades> naoConformidadesList = service.findByDepartamentoId(departamentoId);
        if (naoConformidadesList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(naoConformidadesList);
    }

    @Operation(summary = "Contar não conformidades por ID de departamento", description = "Retorna o número de não conformidades associadas a um departamento específico.")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    @GetMapping("/departamento/{departamentoId}/count")
    public ResponseEntity<Long> countByDepartamentoId(
        @Parameter(description = "ID do departamento para contar as não conformidades") @PathVariable int departamentoId) {
        long count = service.countByDepartamentoId(departamentoId);
        return ResponseEntity.ok(count);
    }
}
