package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.User;
import com.AppFSPH.AppFSPH.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Exibir página de login", description = "Mostra o formulário de login para o usuário.")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Operation(summary = "Exibir formulário de registro", description = "Mostra o formulário de registro para o usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Formulário de registro recuperado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @Operation(summary = "Registrar um novo usuário", description = "Gerencia o registro do usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "302", description = "Usuário registrado com sucesso e redirecionado para login"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida, dados do usuário inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Codificando a senha do usuário antes de salvar
        String encodedPassword = userService.encodePassword(user.getPassword());
        user.setPassword(encodedPassword); // Definindo a senha codificada no objeto User
        userService.saveUser(user); // Salvando o usuário no banco de dados
        return "redirect:/login"; // Redirecionando para a página de login
    }
}
