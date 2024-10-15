package com.AppFSPH.AppFSPH.services;

import com.AppFSPH.AppFSPH.models.NaoConformidades;
import com.AppFSPH.AppFSPH.repositories.NaoConformidadesRepository;
import com.AppFSPH.AppFSPH.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NaoConformidadesService {

    private final NaoConformidadesRepository naoConformidadesRepository;

    @Autowired
    public NaoConformidadesService(NaoConformidadesRepository naoConformidadesRepository) {
        this.naoConformidadesRepository = naoConformidadesRepository;
    }

    // Método para obter todas as não conformidades (Admin)
    public List<NaoConformidades> findAll() {
        User user = getAuthenticatedUser();
        if (user.getRoles().contains(User.Role.ADMIN)) {
            return naoConformidadesRepository.findAll();
        }
        throw new SecurityException("Acesso negado: somente administradores podem ver todas as não conformidades.");
    }

    // Método para encontrar uma não conformidade por ID
    public Optional<NaoConformidades> findById(int id) {
        return naoConformidadesRepository.findById(id);
    }

 // Método para salvar uma nova não conformidade
    public NaoConformidades save(NaoConformidades naoConformidade) {
        User user = getAuthenticatedUser();
        
        // Define o usuário criador
        naoConformidade.setUsuarioCriador(user); 
        naoConformidade.setDataHoraCriacao(LocalDateTime.now()); // Define a data e hora de criação
        naoConformidade.setDataHoraUltimaAlteracao(LocalDateTime.now()); // Define a data e hora da última alteração

        return naoConformidadesRepository.save(naoConformidade);
    }

    // Método para deletar uma não conformidade por ID
    public void deleteById(int id) {
        User user = getAuthenticatedUser();
        if (user.getRoles().contains(User.Role.ADMIN)) {
            naoConformidadesRepository.deleteById(id);
        } else {
            throw new SecurityException("Acesso negado: somente administradores podem deletar não conformidades.");
        }
    }

    // Método para encontrar não conformidades por ID de departamento (Chefe de Departamento)
    public List<NaoConformidades> findByDepartamentoId(int departamentoId) {
        User user = getAuthenticatedUser();
        if (user.getRoles().contains(User.Role.DEPARTAMENTO_CHEFE)) {
            return naoConformidadesRepository.findByDepartamentoId(departamentoId);
        }
        throw new SecurityException("Acesso negado: somente chefes de departamento podem ver as não conformidades do seu departamento.");
    }

    // Método para contar não conformidades por ID de departamento
    public long countByDepartamentoId(int departamentoId) {
        User user = getAuthenticatedUser();
        if (user.getRoles().contains(User.Role.DEPARTAMENTO_CHEFE)) {
            return naoConformidadesRepository.countByDepartamentoId(departamentoId);
        }
        throw new SecurityException("Acesso negado: somente chefes de departamento podem contar as não conformidades do seu departamento.");
    }

    // Método auxiliar para obter o usuário autenticado
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}