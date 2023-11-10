package ru.lct.itmoteam.taskservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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



    @GetMapping("/account")
    public ResponseEntity getUserById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(accountService.getAccountById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось получить данные о пользователе.");
        }
    }

    // TODO: /cabinet
    // TODO: /cabinet/tasks
    // TODO: /cabinet/tasks/route?id=POINT_ID
}
