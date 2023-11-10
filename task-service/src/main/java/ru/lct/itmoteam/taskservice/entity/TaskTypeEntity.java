package ru.lct.itmoteam.taskservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task_type")
public class TaskTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;
    @Column(name = "minutes_to_resolve", nullable = false)
    private int minutesToResolve;
    @Enumerated(EnumType.STRING)
    @Column(name = "required_grade", nullable = false)
    private Grade grade;

    public TaskTypeEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public int getMinutesToResolve() {
        return minutesToResolve;
    }

    public void setMinutesToResolve(int minutesToResolve) {
        this.minutesToResolve = minutesToResolve;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
