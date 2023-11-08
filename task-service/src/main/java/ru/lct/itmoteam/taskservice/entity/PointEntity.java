package ru.lct.itmoteam.taskservice.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "point")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "join_date", nullable = false)
    private Date joinDate;
    @Column(name = "materials_delivered", nullable = false)
    private boolean materialsDelivered;

    public PointEntity() {
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
