package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lct.itmoteam.taskservice.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
