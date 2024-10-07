package com.AppFSPH.AppFSPH.repositories;

import com.AppFSPH.AppFSPH.models.TiposNaoConformidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiposNaoConformidadeRepository extends JpaRepository<TiposNaoConformidade, Integer> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
}
