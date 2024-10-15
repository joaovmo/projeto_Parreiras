package com.AppFSPH.AppFSPH.controllers;

import com.AppFSPH.AppFSPH.models.User;
import com.AppFSPH.AppFSPH.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Codificando a senha do usuário antes de salvar
        String encodedPassword = userService.encodePassword(user.getPassword());
        user.setPassword(encodedPassword); // Definindo a senha codificada no objeto User
        userService.saveUser(user); // Salvando o usuário no banco de dados
        return "redirect:/login"; // Redirecionando para a página de login
    }
}
