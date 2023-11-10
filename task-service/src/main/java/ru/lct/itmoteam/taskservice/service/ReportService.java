package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private TaskService taskService;

    // TODO: получение отчётов: по дате, по двум датам, по сотруднику
    //  returning: List<Report>
    // TODO DTO: Report: first_name, second_name, middle_name, low_priority_tasks_count, medium_..., high_...

}
