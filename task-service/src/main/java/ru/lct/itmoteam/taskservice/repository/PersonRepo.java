package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.PersonEntity;

@Repository
public interface PersonRepo extends JpaRepository<PersonEntity, Long> {
}
