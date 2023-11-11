package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.IssuanceEntity;

import java.util.Date;
import java.util.Optional;

@Repository
public interface IssuanceRepo  extends CrudRepository<IssuanceEntity, Long> {
    @Query("select count(i) from IssuanceEntity i where i.pointEntity.id = ?1")
    int countByPointId(Long id);

    @Query("select max(i.issuingDate) from IssuanceEntity i where i.pointEntity.id = ?1")
    Optional<Date> findMaxDateByPointId(Long id);


}
