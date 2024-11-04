package com.AppFSPH.AppFSPH.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AppFSPH.AppFSPH.models.User;
import com.AppFSPH.AppFSPH.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registrar um novo usuário", description = "Gerencia o registro do usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida, dados do usuário inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute User user) {
        String encodedPassword = userService.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        
        // Preparar o JSON para retornar
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("id", user.getId()); // ID do usuário salvo
        responseJson.put("email", user.getEmail());
        responseJson.put("roles", List.of("USER")); // Ajuste conforme necessário
        responseJson.put("enabled", true); // Ajuste conforme necessário
        responseJson.put("username", user.getUsername());
        responseJson.put("authorities", List.of(Map.of("authority", "ROLE_USER"))); // Ajuste conforme necessário
        responseJson.put("credentialsNonExpired", true);
        responseJson.put("accountNonExpired", true);
        responseJson.put("passwordChanged", true); // Ajuste conforme necessário
        responseJson.put("accountNonLocked", true);

        return ResponseEntity.ok(responseJson); // Retorna o JSON no registro
    }
}