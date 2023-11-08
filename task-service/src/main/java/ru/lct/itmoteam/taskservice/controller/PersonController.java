package ru.lct.itmoteam.taskservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.DTO.Person;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.service.PersonService;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/add")
    public ResponseEntity addPerson(@RequestBody Person person) {
        try {
            personService.registerPerson(person);
            return ResponseEntity.ok("Пользователь успешно добавлен.");
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка во время добавления нового человека.");
        }

    }
}
