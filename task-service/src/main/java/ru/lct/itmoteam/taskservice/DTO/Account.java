package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.AccountEntity;

public class Account {
    private Long id;
    private String login;

    public static Account toModel(AccountEntity account) {
        if (account == null) {
            return null;
        }
        Account model = new Account();
        model.setId(account.getId());
        model.setLogin(account.getLogin());
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
}
