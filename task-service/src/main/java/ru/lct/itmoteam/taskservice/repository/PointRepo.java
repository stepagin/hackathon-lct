package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.PointEntity;

@Repository
public interface PointRepo  extends CrudRepository<PointEntity, Long> {
}
