package ru.lct.itmoteam.taskservice.service;

import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Account;
import ru.lct.itmoteam.taskservice.entity.AccountEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.repository.AccountRepo;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public AccountEntity addAccount(AccountEntity accountEntity) throws BadInputDataException {
        if (accountEntity.getLogin().length() < 3) {
            throw new BadInputDataException("Логин должен быть длиной не менее 3-х символов");
        }
        if (accountEntity.getPassword().length() < 3) {
            throw new BadInputDataException("Пароль должен быть длиной не менее 3-х символов");
        }
        try {
            return accountRepo.save(accountEntity);
        } catch (Exception e) {
            throw new BadInputDataException("Не удалось сохранить аккаунт в базу данных.");
        }
    }

    public Account getAccountById(Long id) throws EntityNotFoundException {
        Optional<AccountEntity> account = accountRepo.findById(id);
        if (account.isPresent()) {
            return Account.toModel(account.get());
        }
        throw new EntityNotFoundException("Пользователь с таким id не найден");
    }

    public AccountEntity getAccountByLogin(String login) throws EntityNotFoundException {
        AccountEntity user = accountRepo.findByLogin(login);
        if (user != null) {
            return user;
        }
        throw new EntityNotFoundException("Пользователь с таким именем не найден");
    }

    private Long deleteAccountById(Long id) {
        accountRepo.deleteById(id);
        return id;
    }

}
