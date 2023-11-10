package ru.lct.itmoteam.taskservice.DTO;


import ru.lct.itmoteam.taskservice.entity.EmployeeEntity;

public class Employee {
    private Long id;
    private String secondName;
    private String firstName;
    private String middleName;
    private String location;
    private String grade;
    private boolean isActive;

    public static Employee toModel(EmployeeEntity employee) {
        if (employee == null)
            return null;
        Employee model = new Employee();
        model.setSecondName(employee.getPerson().getSecondName());
        model.setFirstName(employee.getPerson().getFirstName());
        model.setMiddleName(employee.getPerson().getMiddleName());
        model.setLocation(employee.getLocation().getAddress());
        model.setGrade(employee.getGrade() == null ? null : employee.getGrade().toString());
        model.setActive(employee.isActive());
        return model;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
