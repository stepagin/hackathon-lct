package ru.lct.itmoteam.taskservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.DTO.Person;
import ru.lct.itmoteam.taskservice.entity.PersonEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.repository.PersonRepo;
import ru.lct.itmoteam.taskservice.service.PersonService;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonController {
    private final PersonService personService;
    private final PersonRepo personRepo;

    public PersonController(PersonService personService,
                            PersonRepo personRepo) {
        this.personService = personService;
        this.personRepo = personRepo;
    }

    @PostMapping("/add")
    public ResponseEntity addPerson(@RequestBody Person person) {
        try {
            personService.addPerson(PersonEntity.toEntity(person));
            return ResponseEntity.ok("Пользователь успешно добавлен.");
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка во время добавления нового человека.");
        }

    }

    @GetMapping("/info")
    public ResponseEntity getPersonInfo(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(personService.getPersonById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла внутренняя ошибка сервиса при получении данных сотрудника.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllPersons() {
        try {
            return ResponseEntity.ok(personService.getAllPersons());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла внутренняя ошибка сервиса при получении данных сотрудников.");
        }
    }
}
