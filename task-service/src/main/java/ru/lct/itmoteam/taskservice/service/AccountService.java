package ru.lct.itmoteam.taskservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Account;
import ru.lct.itmoteam.taskservice.entity.AccountEntity;
import ru.lct.itmoteam.taskservice.entity.Role;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.PasswordIncorrectException;
import ru.lct.itmoteam.taskservice.exception.EntityDoesNotExistException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.repository.AccountRepo;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }


    public AccountEntity registration(AccountEntity user) throws BadInputDataException {
        if (accountRepo.findByLogin(user.getLogin()) != null) {
            throw new BadInputDataException("Пользователь с таким именем уже существует");
        } else if (user.getLogin().length() < 3) {
            throw new BadInputDataException("Имя пользоватлея должно быть не менее 3-х символов");
        } else if (user.getPassword().length() < 3) {
            throw new BadInputDataException("Пароль должен быть не меньше 3-х символов");
        }
        user.setPassword(getSHA256Hash(user.getPassword()));
        user.setRole(Role.EMPLOYEE);
        return accountRepo.save(user);
    }

    public AccountEntity login(AccountEntity user) throws EntityDoesNotExistException, PasswordIncorrectException {
        AccountEntity u = accountRepo.findByLogin(user.getLogin());
        if (u == null) {
            throw new EntityDoesNotExistException("Не найден пользователь с таким именем.");
        }
        if (!u.getPassword().equals(getSHA256Hash(user.getPassword()))) {
            throw new PasswordIncorrectException("Неверный пароль.");
        }
        return u;
    }

    public Account getUserById(Long id) throws EntityNotFoundException {
        Optional<AccountEntity> account = accountRepo.findById(id);
        if (account.isPresent()) {
            return Account.toModel(account.get());
        }
        throw new EntityNotFoundException("Пользователь с таким id не найден");
    }

    public AccountEntity getUserByLogin(String login) throws EntityNotFoundException {
        AccountEntity user = accountRepo.findByLogin(login);
        if (user != null) {
            return user;
        }
        throw new EntityNotFoundException("Пользователь с таким именем не найден");
    }

    private Long delete(Long id) {
        accountRepo.deleteById(id);
        return id;
    }

    private String getSHA256Hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
