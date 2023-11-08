package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.AccountEntity;

@Repository
public interface AccountRepo extends CrudRepository<AccountEntity, Long> {
    AccountEntity findByLogin(String login);
}