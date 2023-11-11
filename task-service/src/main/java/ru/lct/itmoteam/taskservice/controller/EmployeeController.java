package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.service.EmployeeService;
import ru.lct.itmoteam.taskservice.service.TaskDistributionService;

@Controller
@RequestMapping("/employees")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TaskDistributionService taskDistributionService;

    @GetMapping
    public ResponseEntity getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось получить данные работников.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getEmployeeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось получить данные работника.");
        }
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity setActiveEmployee(@PathVariable Long employeeId, @RequestParam boolean setActive) {
        try {
            employeeService.setActiveEmployee(employeeId, setActive);
            return ResponseEntity.ok("Успешно обновлено.");
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось обновить.");
        }
    }

    @GetMapping("/{id}/tasksfortoday")
    public ResponseEntity getDistributedTasksForEmployee(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskDistributionService.getEmployeeTasksForToday(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка на стороне сервера.");
        }
    }
}
