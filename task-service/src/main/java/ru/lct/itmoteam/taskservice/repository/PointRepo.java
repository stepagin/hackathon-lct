package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lct.itmoteam.taskservice.entity.PointEntity;

import java.util.Optional;

@Repository
public interface PointRepo  extends CrudRepository<PointEntity, Long> {
    boolean existsById(Long id);

    @Override
    void deleteById(Long id);

    boolean existsByAddress(String address);
    Optional<PointEntity> findByAddress(String address);
}
