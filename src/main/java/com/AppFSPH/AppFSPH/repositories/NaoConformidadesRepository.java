package com.AppFSPH.AppFSPH.repositories;

import com.AppFSPH.AppFSPH.models.NaoConformidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaoConformidadesRepository extends JpaRepository<NaoConformidades, Integer> {
}

//consultas personalizadas
