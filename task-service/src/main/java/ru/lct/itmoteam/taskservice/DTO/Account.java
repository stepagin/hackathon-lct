package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.AccountEntity;

public class Account {
    private Long id;
    private String login;
    private String role;

    public static Account toModel(AccountEntity account) {
        if (account == null) {
            return null;
        }
        Account model = new Account();
        model.setId(account.getId());
        model.setLogin(account.getLogin());
        model.setRole(account.getPerson().getRole().toString());
        return model;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
