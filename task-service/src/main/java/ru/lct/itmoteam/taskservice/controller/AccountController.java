package ru.lct.itmoteam.taskservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.entity.AccountEntity;
import ru.lct.itmoteam.taskservice.exception.PasswordIncorrectException;
import ru.lct.itmoteam.taskservice.exception.BadRegistrationDataException;
import ru.lct.itmoteam.taskservice.exception.UserDoesNotExistException;
import ru.lct.itmoteam.taskservice.exception.UserNotFoundException;
import ru.lct.itmoteam.taskservice.service.AccountService;

@RestController
@RequestMapping("/")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity registration(@RequestBody AccountEntity user) {
        try {
            accountService.registration(user);
            return ResponseEntity.ok(true);
        } catch (BadRegistrationDataException e) {
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
        } catch (PasswordIncorrectException | UserDoesNotExistException e) {
            return ResponseEntity.ok("Введён неверный логин или пароль.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка во время входа в аккаунт.");
        }
    }

    @GetMapping("/person")
    public ResponseEntity getUserById(@RequestParam Long id) {
      try {
          return ResponseEntity.ok(accountService.getUserById(id));
      } catch (UserNotFoundException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      } catch (Exception e) {
          return ResponseEntity.badRequest().body("Не удалось получить данные о пользователе.");
      }
    }
}
