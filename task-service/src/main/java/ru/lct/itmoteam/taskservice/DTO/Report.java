package ru.lct.itmoteam.taskservice.DTO;

public class Report {
    private String firstName;
    private String secondName;
    private String middleName;
    private long lowPriorityTasksCount;
    private long mediumPriorityTasksCount;
    private long highPriorityTasksCount;
    private long delayedTasksCount;

    public Report() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public long getLowPriorityTasksCount() {
        return lowPriorityTasksCount;
    }

    public void setLowPriorityTasksCount(long lowPriorityTasksCount) {
        this.lowPriorityTasksCount = lowPriorityTasksCount;
    }

    public long getMediumPriorityTasksCount() {
        return mediumPriorityTasksCount;
    }

    public void setMediumPriorityTasksCount(long mediumPriorityTasksCount) {
        this.mediumPriorityTasksCount = mediumPriorityTasksCount;
    }

    public long getHighPriorityTasksCount() {
        return highPriorityTasksCount;
    }

    public void setHighPriorityTasksCount(long highPriorityTasksCount) {
        this.highPriorityTasksCount = highPriorityTasksCount;
    }

    public long getDelayedTasksCount() {
        return delayedTasksCount;
    }

    public void setDelayedTasksCount(long delayedTasksCount) {
        this.delayedTasksCount = delayedTasksCount;
    }
}
