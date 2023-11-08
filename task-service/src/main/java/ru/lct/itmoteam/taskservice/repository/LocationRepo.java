package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.LocationEntity;

@Repository
public interface LocationRepo extends CrudRepository<LocationEntity, Long> {
    LocationEntity findByAddress(String address);
}
