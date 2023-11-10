package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.repository.TaskRepo;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private EmployeeService employeeService;

    // TODO: addTask(Task task, TaskType type)
    // TODO: getAllTasks()
    // TODO: getHistory(Date dateFrom, Date dateTo), check: dateFrom <= dateTo
    // TODO: getAllHistory()
    // TODO: getDistributedTasks()
    // TODO: getTaskById(Long id)
    // TODO: bindTaskToEmployee(Long task_id, Long employee_id)
    // TODO: unbindTaskFromEmployee(Long task_id, Long employee_id)
    // TODO: getAllEmployeeTasks(Long employee_id)
    // TODO: int getEmployeeWorkingDay(Long employee_id)
    // TODO: int getTimeAddedByTaskForEmployee(TaskEntity task, EmployeeEntity employee)

}
