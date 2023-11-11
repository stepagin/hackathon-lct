package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lct.itmoteam.taskservice.entity.EmployeeEntity;

import java.util.List;

@Repository
public interface EmployeeRepo extends CrudRepository<EmployeeEntity, Long> {
    @Transactional
    @Modifying
    @Query("update EmployeeEntity e set e.isActive = ?1 where e.id = ?2")
    int updateIsActiveById(boolean isActive, Long id);
    List<EmployeeEntity> findByIsActiveTrue();

}
