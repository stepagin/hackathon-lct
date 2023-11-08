package ru.lct.itmoteam.taskservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.entity.AccountEntity;
import ru.lct.itmoteam.taskservice.exception.PasswordIncorrectException;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityDoesNotExistException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.service.AccountService;

@RestController
@RequestMapping("/")
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity registration(@RequestBody AccountEntity user) {
        try {
            accountService.registration(user);
            return ResponseEntity.ok(true);
        } catch (BadInputDataException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка во время регистрации.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AccountEntity user) {
        try {
            accountService.login(user);
            return ResponseEntity.ok(true);
        } catch (PasswordIncorrectException | EntityDoesNotExistException e) {
            return ResponseEntity.ok("Введён неверный логин или пароль.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка во время входа в аккаунт.");
        }
    }

    @GetMapping("/person")
    public ResponseEntity getUserById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(accountService.getUserById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось получить данные о пользователе.");
        }
    }
}
