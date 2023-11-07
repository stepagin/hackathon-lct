package ru.lct.itmoteam.taskservice.controller;

import ru.lct.itmoteam.taskservice.model.UserEntity;
import ru.lct.itmoteam.taskservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam UserEntity user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        // Реализация метода авторизации
        // Надо сравнивать переданный username и password с данными в БД
        // Если авторизация успешна, перенаправляем на страницу приложения
        // В противном случае, возвращаемся на страницу входа с сообщением об ошибке
        return "redirect:/app";
    }
}
