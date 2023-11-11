package ru.lct.itmoteam.taskservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.AccountAndPersonData;
import ru.lct.itmoteam.taskservice.entity.*;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityDoesNotExistException;
import ru.lct.itmoteam.taskservice.exception.PasswordIncorrectException;
import ru.lct.itmoteam.taskservice.repository.AccountRepo;

@Service
public class AuthService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private PersonService personService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmployeeService employeeService;

    public AccountEntity register(AccountAndPersonData data) throws BadInputDataException {
        if (accountRepo.existsAccountEntityByLogin(data.getLogin())) {
            throw new BadInputDataException("Пользователь с таким логином уже существует");
        }
        if (!Role.isCorrect(data.getRole())) {
            throw new BadInputDataException("Неверно введена роль. Роль может быть EMPLOYEE или MANAGER.");
        }
        if (data.getSecondName() == null || data.getFirstName() == null || data.getMiddleName() == null) {
            throw new BadInputDataException("Неверно введено ФИО.");
        }
        if (data.getSecondName().equals("") || data.getFirstName().equals("")) {
            throw new BadInputDataException("Неверно введено ФИО.");
        }
        // Проходит регистрация 3 раза: человека, аккаунта и работника

        if (data.getRole().equals("EMPLOYEE")) {
            if (!Grade.isCorrect(data.getGrade())) {
                throw new BadInputDataException("Неверно указан грейд сотрудника.");
            }
            if (!locationService.existsByAddress(data.getLocation())) {
                throw new BadInputDataException("Не существует точек с таким адресом.");
            }
            if (data.getLogin().length() < 3) {
                throw new BadInputDataException("Логин должен быть длиной не менее 3-х символов");
            }
            if (data.getPassword().length() < 3) {
                throw new BadInputDataException("Пароль должен быть длиной не менее 3-х символов");
            }
            if (!data.isActive()) {
                data.setActive(false);
            }

        }
        try {
            PersonEntity personEntity = personService.addPerson(PersonEntity.toEntity(AccountAndPersonData.getPerson(data)));
            data.setPassword(getSHA256Hash(data.getPassword()));
            AccountEntity accountEntity = accountService.addAccount(new AccountEntity(data, personEntity));
            if (data.getRole().equals("EMPLOYEE")) {
                EmployeeEntity employeeEntity = new EmployeeEntity();
                employeeEntity.setLocation(locationService.getLocationEntityByAddress(data.getLocation()));
                employeeEntity.setPerson(personEntity);
                employeeEntity.setGrade(Grade.valueOf(data.getGrade()));
                employeeEntity.setActive(data.isActive());
                employeeService.addEmployee(employeeEntity);
            }
            return accountEntity;
        } catch (BadInputDataException e) {
            throw new BadInputDataException(e.getMessage());
        } catch (Exception e) {
            throw new BadInputDataException("Произошла ошибка во время регистрации.");
        }
    }

    public AccountEntity login(AccountEntity account) throws EntityDoesNotExistException, PasswordIncorrectException {
        if (!accountRepo.existsAccountEntityByLogin(account.getLogin()))
            throw new EntityDoesNotExistException("Не найден пользователь с таким именем.");
        AccountEntity user = accountRepo.findByLogin(account.getLogin());
        if (!user.getPassword().equals(getSHA256Hash(account.getPassword()))) {
            throw new PasswordIncorrectException("Неверный пароль.");
        }
        return user;
    }

    private String getSHA256Hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
