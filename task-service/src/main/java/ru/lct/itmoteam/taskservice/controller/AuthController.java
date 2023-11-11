package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lct.itmoteam.taskservice.DTO.AccountAndPersonData;
import ru.lct.itmoteam.taskservice.DTO.AuthData;
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
            return ResponseEntity.ok(new AuthData(true, authService.register(data).getPerson()));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(new AuthData(false, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла внутренняя ошибка сервиса.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AccountEntity user) {
        try {
            return ResponseEntity.ok(new AuthData(true, authService.login(user).getPerson()));
        } catch (PasswordIncorrectException | EntityDoesNotExistException e) {
            return ResponseEntity.status(403).body(new AuthData(false, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка во время входа в аккаунт.");
        }
    }
}
