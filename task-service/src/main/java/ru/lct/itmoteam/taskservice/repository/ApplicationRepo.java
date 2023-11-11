package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.ApplicationEntity;

import java.util.Date;

@Repository
public interface ApplicationRepo extends CrudRepository<ApplicationEntity, Long> {
    @Query("select count(a) from ApplicationEntity a where a.pointEntity.id = ?1")
    int getApplicationCountByPointId(Long id);

    @Query("select count(a) from ApplicationEntity a where a.completed = false and a.applicationDate >= ?1")
    long countApplicationsFromDate(Date applicationDate);


}
