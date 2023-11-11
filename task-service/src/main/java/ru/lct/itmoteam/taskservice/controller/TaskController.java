package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.DTO.Task;
import ru.lct.itmoteam.taskservice.entity.TaskEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.service.EmployeeService;
import ru.lct.itmoteam.taskservice.service.TaskService;

@Controller
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity getAllUnfinishedTasks() {
        try {
            return ResponseEntity.ok(taskService.getAllUnfinishedTasks());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось получить незавершённые задачи.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskService.getTaskById(id));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось запросить задачу.");
        }
    }

    @GetMapping("/history")
    public ResponseEntity getAllFinishedTasks() {
        try {
            return ResponseEntity.ok(taskService.getAllFinishedTasks());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось получить завершённые задачи.");
        }
    }

    @PostMapping("/add")
    public ResponseEntity addTask(@RequestBody Task task) {
        try {
            TaskEntity savedEntity = taskService.addTask(task);
            return ResponseEntity.ok(Task.toModel(savedEntity));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось обработать запрос.");
        }
    }


    @PostMapping("/{taskId}/assign")
    public ResponseEntity assignTask(@PathVariable Long taskId, @RequestParam Long employeeId) {
        try {
            return ResponseEntity.ok(taskService.assignTaskToEmployee(taskId, employeeId));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось назначить задачу.");
        }
    }

    @PostMapping("/{taskId}/unassign")
    public ResponseEntity unassignTask(@PathVariable Long taskId) {
        try {
            return ResponseEntity.ok(taskService.unassignTask(taskId));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось отвязать задачу.");
        }
    }

    @PostMapping("/{taskId}/done")
    public ResponseEntity taskDone(@PathVariable Long taskId) {
        try {
            taskService.finishTask(taskId);
            return ResponseEntity.ok("Успешно.");
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Не удалось пометить задачу выполненной.");
        }
    }


}
