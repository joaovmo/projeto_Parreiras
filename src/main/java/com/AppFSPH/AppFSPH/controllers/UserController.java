package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.User;
import com.AppFSPH.AppFSPH.models.User.Role;
import com.AppFSPH.AppFSPH.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários.")
    @PreAuthorize("isAuthenticated()") // Apenas usuários autenticados podem acessar
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PreAuthorize("isAuthenticated()") // Apenas usuários autenticados podem acessar
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
        @Parameter(description = "ID do usuário a ser buscado") @PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Adicionar roles a um usuário", description = "Adiciona roles ao usuário especificado pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Roles adicionadas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')") // Apenas administradores podem acessar
    @PutMapping("/{id}/roles")
    public ResponseEntity<User> addRoleToUser(
        @Parameter(description = "ID do usuário ao qual as roles serão adicionadas") @PathVariable Long id, 
        @RequestBody List<Role> roles) {
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
