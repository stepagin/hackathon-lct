package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.PersonEntity;

public class Person {
    private Long id;
    private String secondName;
    private String firstName;
    private String middleName;
    private String role;


    public static Person toModel(PersonEntity person) {
        if (person == null)
            return null;
        Person model = new Person();
        model.setId(person.getId());
        model.setFirstName(person.getFirstName());
        model.setSecondName(person.getSecondName());
        model.setMiddleName(person.getMiddleName());
        model.setRole(person.getRole().toString());
        return model;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
