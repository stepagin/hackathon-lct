package ru.lct.itmoteam.taskservice.DTO;

import java.util.Date;

public class PointStatistic {
    private Long id;
    private String address;
    private Date joinDate;
    private boolean materialsDelivered;
    private int daysFromLastCardIssuanse;
    private int applicationsCount;
    private int issuanceCount;

    public PointStatistic() {
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

    public int getDaysFromLastCardIssuanse() {
        return daysFromLastCardIssuanse;
    }

    public void setDaysFromLastCardIssuanse(int daysFromLastCardIssuanse) {
        this.daysFromLastCardIssuanse = daysFromLastCardIssuanse;
    }

    public int getApplicationsCount() {
        return applicationsCount;
    }

    public void setApplicationsCount(int applicationsCount) {
        this.applicationsCount = applicationsCount;
    }

    public int getIssuanceCount() {
        return issuanceCount;
    }

    public void setIssuanceCount(int issuanceCount) {
        this.issuanceCount = issuanceCount;
    }
}
