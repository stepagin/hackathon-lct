package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDistributionService {
    @Autowired
    TaskService taskService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PointService pointService;

    // TODO: int getTimeAddedByTaskForEmployee(TaskEntity task, EmployeeEntity employee)
    // TODO: int getEmployeeWorkingDay(Long employee_id)

    // TODO: assignTaskAutomatically(Long task_id)
}
