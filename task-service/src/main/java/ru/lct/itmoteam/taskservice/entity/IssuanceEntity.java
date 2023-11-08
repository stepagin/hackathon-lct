package ru.lct.itmoteam.taskservice.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "issuance")
public class IssuanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "issuing_date", nullable = false)
    private Date issuingDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "point_id")
    private PointEntity pointEntity;

    public IssuanceEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(Date issuingDate) {
        this.issuingDate = issuingDate;
    }

    public PointEntity getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(PointEntity pointEntity) {
        this.pointEntity = pointEntity;
    }
}
