package ru.lct.itmoteam.taskservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Account;
import ru.lct.itmoteam.taskservice.entity.AccountEntity;
import ru.lct.itmoteam.taskservice.exception.BadRegistrationDataException;
import ru.lct.itmoteam.taskservice.exception.PasswordIncorrectException;
import ru.lct.itmoteam.taskservice.exception.UserDoesNotExistException;
import ru.lct.itmoteam.taskservice.exception.UserNotFoundException;
import ru.lct.itmoteam.taskservice.repository.AccountRepo;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;


    public AccountEntity registration(AccountEntity user) throws BadRegistrationDataException {
        if (accountRepo.findByLogin(user.getLogin()) != null) {
            throw new BadRegistrationDataException("Пользователь с таким именем уже существует");
        } else if (user.getLogin().length() < 3) {
            throw new BadRegistrationDataException("Имя пользоватлея должно быть не менее 3-х символов");
        } else if (user.getPassword().length() < 3) {
            throw new BadRegistrationDataException("Пароль должен быть не меньше 3-х символов");
        }
        user.setPassword(getSHA256Hash(user.getPassword()));
        user.setRole(AccountEntity.Role.EMPLOYEE);
        return accountRepo.save(user);
    }

    public AccountEntity login(AccountEntity user) throws UserDoesNotExistException, PasswordIncorrectException {
        AccountEntity u = accountRepo.findByLogin(user.getLogin());
        if (u == null) {
            throw new UserDoesNotExistException("Не найден пользователь с таким именем.");
        }
        if (!u.getPassword().equals(getSHA256Hash(user.getPassword()))) {
            throw new PasswordIncorrectException("Неверный пароль.");
        }
        return u;
    }

    public Account getUserById(Long id) throws UserNotFoundException {
        Optional<AccountEntity> user = accountRepo.findById(id);
        if (user.isPresent()) {
            return Account.toModel(user.get());
        }
        throw new UserNotFoundException("Пользователь с таким id не найден");
    }

    public AccountEntity getUserByLogin(String login) throws UserNotFoundException {
        AccountEntity user = accountRepo.findByLogin(login);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException("Пользователь с таким именем не найден");
    }

    private Long delete(Long id) {
        accountRepo.deleteById(id);
        return id;
    }

    @Autowired
    public void setAccountRepo(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    private String getSHA256Hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
