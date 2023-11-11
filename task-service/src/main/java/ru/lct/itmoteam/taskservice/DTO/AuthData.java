package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.PersonEntity;

public class AuthData {
    private boolean success;
    private Person user;


    public AuthData() {
    }

    public AuthData (boolean success, PersonEntity person) {
        if (success) {
            this.success = true;
            this.user = Person.toModel(person);
        } else {
            this.success = false;
            user = null;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }
}
