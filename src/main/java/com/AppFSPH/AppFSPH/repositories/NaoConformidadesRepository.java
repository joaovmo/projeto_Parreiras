package com.AppFSPH.AppFSPH.repositories;

import com.AppFSPH.AppFSPH.models.NaoConformidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NaoConformidadesRepository extends JpaRepository<NaoConformidades, Integer> {
    
    List<NaoConformidades> findByDepartamentoId(int departamentoId);
    
    long countByDepartamentoId(int departamentoId);
    
    // Consulta para encontrar não conformidades por statusId
    List<NaoConformidades> findByStatusId(int statusId);
    
    // Consulta para encontrar não conformidades criadas por um usuário específico
    List<NaoConformidades> findByUsuarioNome(String usuarioNome);
    
    // Consulta para contar não conformidades por statusId
    long countByStatusId(int statusId);
    
    // Outras consultas personalizadas podem ser adicionadas aqui conforme necessário
}
