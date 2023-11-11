package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.PointEntity;

import java.util.Date;

public class Point {
    private Long id;
    private String address;
    private Date joinDate;
    private boolean materialsDelivered;

    public Point() {
    }

    public static Point toModel(PointEntity point) {
        Point model = new Point();
        model.setId(point.getId());
        model.setJoinDate(point.getJoinDate());
        model.setAddress(point.getAddress());
        model.setMaterialsDelivered(point.isMaterialsDelivered());
        return model;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isMaterialsDelivered() {
        return materialsDelivered;
    }

    public void setMaterialsDelivered(boolean materialsDelivered) {
        this.materialsDelivered = materialsDelivered;
    }
}
