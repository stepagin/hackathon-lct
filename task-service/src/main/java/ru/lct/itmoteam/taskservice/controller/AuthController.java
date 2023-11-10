package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lct.itmoteam.taskservice.DTO.AccountAndPersonData;
import ru.lct.itmoteam.taskservice.entity.AccountEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityDoesNotExistException;
import ru.lct.itmoteam.taskservice.exception.PasswordIncorrectException;
import ru.lct.itmoteam.taskservice.service.AuthService;

@Controller
@RequestMapping("/")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AccountAndPersonData data) {
        try {
            authService.register(data);
            return ResponseEntity.ok("Пользователь успешно зарегистрирован");
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла внутренняя ошибка сервиса.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AccountEntity user) {
        try {
            authService.login(user);
            return ResponseEntity.ok(true);
        } catch (PasswordIncorrectException | EntityDoesNotExistException e) {
            return ResponseEntity.ok("Введён неверный логин или пароль.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка во время входа в аккаунт.");
        }
    }
}
