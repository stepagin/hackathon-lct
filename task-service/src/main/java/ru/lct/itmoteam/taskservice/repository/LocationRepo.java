package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.LocationEntity;

@Repository
public interface LocationRepo extends JpaRepository<LocationEntity, Long> {
    boolean existsByAddress(String address);
    LocationEntity findByAddress(String address);
}
