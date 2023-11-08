package ru.lct.itmoteam.taskservice.service;

import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Person;
import ru.lct.itmoteam.taskservice.entity.PersonEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.repository.PersonRepo;

import java.util.Objects;
import java.util.Optional;

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

    public boolean registerPerson(Person person) throws BadInputDataException {
        if (person.getFirstName() == null || Objects.equals(person.getFirstName(), ""))
            throw new BadInputDataException("Имя не может быть пустым.");

        if (person.getSecondName() == null || Objects.equals(person.getSecondName(), ""))
            throw new BadInputDataException("Фамилия не может быть пустой.");

        if (person.getMiddleName() == null || Objects.equals(person.getMiddleName(), ""))
            throw new BadInputDataException("Отчество не может быть пустым.");

        if (person.getLocation() == null)
            throw new BadInputDataException("Невозможно добавить человека, не указав id офиса.");

        if (locationService.getLocationModelById(person.getLocation()) == null)
            throw new BadInputDataException("Офиса с таким id не существует");

        // TODO: CHECK person.getGrade() IN Grade


        try {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setFirstName(person.getFirstName());
            personEntity.setSecondName(person.getSecondName());
            personEntity.setMiddleName(person.getMiddleName());
            personEntity.setLocation(locationService.getLocationEntityById(person.getLocation()));
            personEntity.setGrade(person.getGrade());
            personEntity.setActive(true);
            personRepo.save(personEntity);
            return true;
        } catch (Exception e) {
            throw new BadInputDataException("Произошла ошибка во время добавления человека.");
        }
    }

}
