package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.NaoConformidades;
import com.AppFSPH.AppFSPH.services.NaoConformidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nao-conformidades")
public class NaoConformidadesController {

    @Autowired
    private NaoConformidadesService service;

    @GetMapping
    public List<NaoConformidades> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NaoConformidades> getById(@PathVariable int id) {
        NaoConformidades naoConformidade = service.findById(id);
        if (naoConformidade != null) {
            return ResponseEntity.ok(naoConformidade);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public NaoConformidades create(@RequestBody NaoConformidades naoConformidade) {
        return service.save(naoConformidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NaoConformidades> update(@PathVariable int id, @RequestBody NaoConformidades naoConformidade) {
        if (service.findById(id) != null) {
            naoConformidade.setId(id);
            return ResponseEntity.ok(service.save(naoConformidade));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (service.findById(id) != null) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
