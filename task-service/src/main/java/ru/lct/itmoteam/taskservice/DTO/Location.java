package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.LocationEntity;

public class Location {
    private Long id;
    private String address;

    public static Location toModel(LocationEntity locationEntity) {
        if (locationEntity == null)
            return null;
        Location location = new Location();
        location.setId(locationEntity.getId());
        location.setAddress(locationEntity.getAddress());
        return location;
    }

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
