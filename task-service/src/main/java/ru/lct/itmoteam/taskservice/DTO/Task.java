package ru.lct.itmoteam.taskservice.DTO;

import ru.lct.itmoteam.taskservice.entity.TaskEntity;

import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    private Long id;
    private String name;
    private String priority;
    private String requiredGrade;
    private Date assignmentDate;
    private int minutesToResolve;
    private boolean completed;
    private LocalDateTime completionDate;
    private String status;
    private Long personId;

    public static Task toModel(TaskEntity taskEntity) {
        Task task = new Task();
        task.setId(taskEntity.getId());
        task.setName(taskEntity.getType().getName());
        task.setPriority(taskEntity.getType().getPriority().toString());
        task.setRequiredGrade(taskEntity.getType().getGrade().toString());
        task.setAssignmentDate(taskEntity.getAssignmentDate());
        task.setMinutesToResolve(taskEntity.getType().getMinutesToResolve());
        task.setCompleted(taskEntity.isCompleted());
        task.setCompletionDate(taskEntity.getCompletionDatetime());
        task.setStatus(task.getStatus());
        task.setPersonId(taskEntity.getPerson().getId());
        return task;
    }

    public Task() {
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequiredGrade() {
        return requiredGrade;
    }

    public void setRequiredGrade(String requiredGrade) {
        this.requiredGrade = requiredGrade;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public int getMinutesToResolve() {
        return minutesToResolve;
    }

    public void setMinutesToResolve(int minutesToResolve) {
        this.minutesToResolve = minutesToResolve;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
