package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lct.itmoteam.taskservice.service.EmployeeService;
import ru.lct.itmoteam.taskservice.service.TaskService;

@Controller
@RequestMapping("/main")
@CrossOrigin
public class MainPageController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;

    // TODO: returning tasks_for_today, active_tasks, active_employees
    //  create DTO for that

}
