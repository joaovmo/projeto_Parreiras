package com.AppFSPH.AppFSPH.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AppFSPH.AppFSPH.models.User;
import com.AppFSPH.AppFSPH.services.UserService;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Página de login"); // Ou retorne um objeto que represente a página de login
    }

    @GetMapping("/register")
    public ResponseEntity<?> registerForm() {
        return ResponseEntity.ok("Formulário de registro"); // Retorne uma mensagem ou um objeto com informações do registro
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute User user) {
        String encodedPassword = userService.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        return ResponseEntity.ok("Usuário registrado com sucesso"); // Retorna sucesso
    }
}
