package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.NaoConformidades;
import com.AppFSPH.AppFSPH.services.NaoConformidadesService;
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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE') or hasRole('CONTROLE_QUALIDADE')")
    public ResponseEntity<List<NaoConformidades>> getAll() {
        List<NaoConformidades> naoConformidadesList = service.findAll();
        return ResponseEntity.ok(naoConformidadesList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE') or hasRole('CONTROLE_QUALIDADE')")
    public ResponseEntity<NaoConformidades> getById(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método de criação consolidado
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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    public ResponseEntity<NaoConformidades> update(@PathVariable int id, @RequestBody NaoConformidades naoConformidade) {
        if (service.findById(id).isPresent()) {
            naoConformidade.setId(id);
            naoConformidade.setDataHoraUltimaAlteracao(LocalDateTime.now());
            NaoConformidades updatedNaoConformidade = service.save(naoConformidade);
            return ResponseEntity.ok(updatedNaoConformidade);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/departamento/{departamentoId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    public ResponseEntity<List<NaoConformidades>> getByDepartamentoId(@PathVariable int departamentoId) {
        List<NaoConformidades> naoConformidadesList = service.findByDepartamentoId(departamentoId);
        if (naoConformidadesList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(naoConformidadesList);
    }

    @GetMapping("/departamento/{departamentoId}/count")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DEPARTAMENTO_CHEFE')")
    public ResponseEntity<Long> countByDepartamentoId(@PathVariable int departamentoId) {
        long count = service.countByDepartamentoId(departamentoId);
        return ResponseEntity.ok(count);
    }
}
