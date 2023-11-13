package ru.lct.itmoteam.taskservice.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "dated_task_for_employee")
public class DatedTaskForEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "doing_date", nullable = false, columnDefinition = "date")
    private Date doingDate;
    @Column(name = "task_id", nullable = false)
    private Long TaskId;
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "previous_dated_task")
    private Long previousDatedTask;
    @Column(name = "next_dated_task")
    private Long nextDatedTask;
    @Column(name = "minutes_to_go", nullable = false)
    private int minutesToGo;

    public DatedTaskForEmployeeEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDoingDate() {
        return doingDate;
    }

    public void setDoingDate(Date doingDate) {
        this.doingDate = doingDate;
    }

    public Long getTaskId() {
        return TaskId;
    }

    public void setTaskId(Long taskId) {
        TaskId = taskId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getPreviousDatedTask() {
        return previousDatedTask;
    }

    public void setPreviousDatedTask(Long previousDatedTask) {
        this.previousDatedTask = previousDatedTask;
    }

    public Long getNextDatedTask() {
        return nextDatedTask;
    }

    public void setNextDatedTask(Long nextDatedTask) {
        this.nextDatedTask = nextDatedTask;
    }

    public int getMinutesToGo() {
        return minutesToGo;
    }

    public void setMinutesToGo(int minutesToGo) {
        this.minutesToGo = minutesToGo;
    }
}
