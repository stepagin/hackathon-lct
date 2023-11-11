package ru.lct.itmoteam.taskservice.service;

import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Person;
import ru.lct.itmoteam.taskservice.entity.PersonEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.repository.PersonRepo;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class PersonService {
    private final PersonRepo personRepo;
    private final LocationService locationService;

    public PersonService(PersonRepo personRepo, LocationService locationService) {
        this.personRepo = personRepo;
        this.locationService = locationService;
    }

    public Person getPersonById(Long id) throws EntityNotFoundException {
        Optional<PersonEntity> person = personRepo.findById(id);
        if (person.isPresent()) {
            return Person.toModel(person.get());
        }
        throw new EntityNotFoundException("Пользователь с таким id не найден");
    }

    public List<Person> getAllPersons() {
        Iterable<PersonEntity> source = personRepo.findAll();
        return StreamSupport.stream(source.spliterator(), false)
                .map(Person::toModel)
                .toList();
    }

    public PersonEntity addPerson(PersonEntity personEntity) throws BadInputDataException {
        if (personEntity.getFirstName() == null || Objects.equals(personEntity.getFirstName(), ""))
            throw new BadInputDataException("Имя не может быть пустым.");

        if (personEntity.getSecondName() == null || Objects.equals(personEntity.getSecondName(), ""))
            throw new BadInputDataException("Фамилия не может быть пустой.");

        if (personEntity.getMiddleName() == null || Objects.equals(personEntity.getMiddleName(), ""))
            throw new BadInputDataException("Отчество не может быть пустым.");

        try {
            PersonEntity entity = new PersonEntity();
            entity.setFirstName(personEntity.getFirstName());
            entity.setSecondName(personEntity.getSecondName());
            entity.setMiddleName(personEntity.getMiddleName());
            entity.setRole(personEntity.getRole());
            return personRepo.save(entity);
        } catch (Exception e) {
            throw new BadInputDataException("Произошла ошибка во время добавления человека.");
        }
    }

}
