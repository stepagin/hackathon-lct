package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.ApplicationEntity;

@Repository
public interface ApplicationRepo extends CrudRepository<ApplicationEntity, Long> {
    @Query("select count(a) from ApplicationEntity a where a.pointEntity.id = ?1")
    int getApplicationCountByPointId(Long id);

}
