package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.Grade;
import ru.lct.itmoteam.taskservice.entity.PersonEntity;

public class Person {
    private Long id;
    private String secondName;
    private String firstName;
    private String middleName;
    private Long location;
    private Grade grade;


    public static Person toModel(PersonEntity personEntity) {
        Person person = new Person();
        person.setId(personEntity.getId());
        person.setFirstName(personEntity.getFirstName());
        person.setSecondName(personEntity.getSecondName());
        person.setMiddleName(personEntity.getMiddleName());
        person.setLocation(personEntity.getLocation().getId());
        person.setGrade(personEntity.getGrade());
        return person;
    }

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
