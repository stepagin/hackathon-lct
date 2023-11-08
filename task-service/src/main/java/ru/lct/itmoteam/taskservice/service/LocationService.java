package ru.lct.itmoteam.taskservice.service;

import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Location;
import ru.lct.itmoteam.taskservice.entity.LocationEntity;
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

    public Location getLocationByAddress(String address) {
        return Location.toModel(locationRepo.findByAddress(address));
    }
}
