package ru.lct.itmoteam.taskservice.entity;

import jakarta.persistence.*;
import ru.lct.itmoteam.taskservice.DTO.Person;

@Entity
@Table(name = "person")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "second_name", nullable = false)
    private String secondName;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name", nullable = false)
    private String middleName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public PersonEntity() {
    }

    public static PersonEntity toEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        try {
            entity.setRole(Role.valueOf(person.getRole()));
        } catch (Exception e) {
            entity.setRole(null);
        }
        entity.setFirstName(person.getFirstName());
        entity.setSecondName(person.getSecondName());
        entity.setMiddleName(person.getMiddleName());
        return entity;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
