package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.TaskTypeEntity;

@Repository
public interface TaskTypeRepo  extends CrudRepository<TaskTypeEntity, Long> {
    boolean existsById(Long id);
    boolean existsByName(String name);
    TaskTypeEntity findByName(String name);
}
