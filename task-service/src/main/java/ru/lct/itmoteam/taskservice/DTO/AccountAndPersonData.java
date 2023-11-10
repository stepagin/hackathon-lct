package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.AccountEntity;
import ru.lct.itmoteam.taskservice.entity.Role;

public class AccountAndPersonData {
    private String role;
    private String secondName;
    private String firstName;
    private String middleName;
    private String login;
    private String password;
    private String location;
    private String grade;
    private boolean isActive;

    public static Person getPerson(AccountAndPersonData data) {
        Person person = new Person();
        person.setRole(data.getRole());
        person.setSecondName(data.getSecondName());
        person.setFirstName(data.getFirstName());
        person.setMiddleName(data.getMiddleName());
        return person;
    }

    public static AccountEntity getAccountEntity(AccountAndPersonData data) {
        AccountEntity account = new AccountEntity();
        account.setLogin(data.getLogin());
        account.setPassword(data.getPassword());
        return account;
    }

    public AccountAndPersonData() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
