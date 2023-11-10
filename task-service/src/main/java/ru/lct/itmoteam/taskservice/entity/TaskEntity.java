package ru.lct.itmoteam.taskservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type")
    private TaskTypeEntity type;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "point_id")
    private PointEntity point;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskDistributionStatus status;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private PersonEntity person;
    @Column(name = "assignment_date")
    private Date assignmentDate;
    @Column(name = "completed")
    private boolean completed;
    @Column(name = "completion_date")
    private LocalDateTime completionDatetime;

    public TaskEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskTypeEntity getType() {
        return type;
    }

    public void setType(TaskTypeEntity type) {
        this.type = type;
    }

    public PointEntity getPoint() {
        return point;
    }

    public void setPoint(PointEntity point) {
        this.point = point;
    }

    public TaskDistributionStatus getStatus() {
        return status;
    }

    public void setStatus(TaskDistributionStatus status) {
        this.status = status;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletionDatetime() {
        return completionDatetime;
    }

    public void setCompletionDatetime(LocalDateTime completionDatetime) {
        this.completionDatetime = completionDatetime;
    }
}
