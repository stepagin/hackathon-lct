package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Employee;
import ru.lct.itmoteam.taskservice.DTO.Report;
import ru.lct.itmoteam.taskservice.DTO.Task;
import ru.lct.itmoteam.taskservice.entity.EmployeeEntity;
import ru.lct.itmoteam.taskservice.entity.Grade;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;

    public Report makeReportByEmployeeId(Long emoloyeeId) throws BadInputDataException {
        EmployeeEntity employee = employeeService.getEmployeeEntityById(emoloyeeId);
        Report report = new Report();
        report.setSecondName(employee.getPerson().getSecondName());
        report.setFirstName(employee.getPerson().getFirstName());
        report.setMiddleName(employee.getPerson().getMiddleName());
        report.setLowPriorityTasksCount(taskService.getTasksCountByEmployeeAndGrade(emoloyeeId, Grade.JUNIOR));
        report.setMediumPriorityTasksCount(taskService.getTasksCountByEmployeeAndGrade(emoloyeeId, Grade.MIDDLE));
        report.setHighPriorityTasksCount(taskService.getTasksCountByEmployeeAndGrade(emoloyeeId, Grade.SENIOR));
        report.setDelayedTasksCount(getDelayedTasks(taskService.getAllFinishedTasksByEmployeeId(emoloyeeId)));
        return report;
    }

    public long getDelayedTasks(List<Task> taskList) {
        return taskList.stream()
                .filter(task -> task.getCompletionDate() != null)
                .filter(task -> task.getAssignmentDate().toInstant()
                        .isBefore(
                                task.getCompletionDate().minusDays(1).toInstant(ZoneOffset.ofHours(0))))
                .count();
    }

    public List<Report> makeAllReports() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<Report> reports = new ArrayList<>();
        for (Employee employee :
                employees) {
            try {
                reports.add(makeReportByEmployeeId(employee.getId()));
            } catch (BadInputDataException ignored) {
            }
        }
        return reports;
    }

    public List<Report> makeReportsBetweenDate(Date from, Date to) {
        List<Employee> employees = employeeService.getAllEmployees();
        List<Report> reports = new ArrayList<>();
        for (Employee employee :
                employees) {
            try {
                reports.add(makeReportByEmployeeIdAndDates(employee.getId(), from, to));
            } catch (BadInputDataException ignored) {
            }
        }
        return reports;
    }

    public Report makeReportByEmployeeIdAndDates(Long emoloyeeId, Date from, Date to) throws BadInputDataException {
        EmployeeEntity employee = employeeService.getEmployeeEntityById(emoloyeeId);
        Report report = new Report();
        report.setSecondName(employee.getPerson().getSecondName());
        report.setFirstName(employee.getPerson().getFirstName());
        report.setMiddleName(employee.getPerson().getMiddleName());
        report.setLowPriorityTasksCount(taskService.getTasksCountByEmployeeAndGradeAndDates(emoloyeeId, Grade.JUNIOR, from, to));
        report.setMediumPriorityTasksCount(taskService.getTasksCountByEmployeeAndGradeAndDates(emoloyeeId, Grade.MIDDLE, from, to));
        report.setHighPriorityTasksCount(taskService.getTasksCountByEmployeeAndGradeAndDates(emoloyeeId, Grade.SENIOR, from, to));
        report.setDelayedTasksCount(getDelayedTasks(taskService.getAllFinishedTasksByEmployeeId(emoloyeeId)));
        return report;
    }


}
