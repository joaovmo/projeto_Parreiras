package com.AppFSPH.AppFSPH.services;

import com.AppFSPH.AppFSPH.models.NaoConformidades;
import com.AppFSPH.AppFSPH.repositories.NaoConformidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NaoConformidadesService {

    @Autowired
    private NaoConformidadesRepository repository;

    public List<NaoConformidades> findAll() {
        return repository.findAll();
    }

    public NaoConformidades findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public NaoConformidades save(NaoConformidades naoConformidade) {
        return repository.save(naoConformidade);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
