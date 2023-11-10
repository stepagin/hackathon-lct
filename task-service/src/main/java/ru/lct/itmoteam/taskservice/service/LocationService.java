package ru.lct.itmoteam.taskservice.service;

import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Location;
import ru.lct.itmoteam.taskservice.entity.LocationEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.repository.LocationRepo;

import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepo locationRepo;

    public LocationService(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Location getLocationModelById(Long id) {
        Optional<LocationEntity> locationEntityOptional = locationRepo.findById(id);
        return locationEntityOptional.map(Location::toModel).orElse(null);
    }
    public LocationEntity getLocationEntityById(Long id) {
        Optional<LocationEntity> locationEntityOptional = locationRepo.findById(id);
        return locationEntityOptional.orElse(null);
    }

    public LocationEntity getLocationEntityByAddress(String address) {
        return locationRepo.findByAddress(address);
    }

    public boolean existsByAddress(String address) {
        return locationRepo.existsByAddress(address);
    }

    public LocationEntity addLocation(LocationEntity location) throws BadInputDataException {
        if (this.existsByAddress(location.getAddress())) {
            throw new BadInputDataException("Офис с таким адресом уже существует.");
        }
        try {
            return locationRepo.save(location);
        } catch (Exception e) {
            throw new BadInputDataException("Не удалось добавить офис в базу данных.");
        }
    }
}
