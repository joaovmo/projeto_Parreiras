package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.User;
import com.AppFSPH.AppFSPH.models.User.Role;
import com.AppFSPH.AppFSPH.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Método para listar todos os usuários
    @PreAuthorize("isAuthenticated()") // Apenas usuários autenticados podem acessar
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // Método para buscar um usuário por ID
    @PreAuthorize("isAuthenticated()") // Apenas usuários autenticados podem acessar
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método para adicionar roles a um usuário
    @PutMapping("/{id}/roles") // Corrigido: removido 'users'
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addRoleToUser(@PathVariable Long id, @RequestBody List<Role> roles) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Adiciona todas as novas roles
            if (roles != null) {
                for (Role role : roles) {
                    if (!user.getRoles().contains(role)) {
                        user.getRoles().add(role);
                    }
                }
            }
            
            userService.saveUser(user); // Salva o usuário atualizado
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

