package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.service.TaskDistributionService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
@CrossOrigin
public class TaskDistributionController {
    @Autowired
    private TaskDistributionService taskDistributionService;

    @PostMapping("/distribution/start")
    public ResponseEntity distributeTasks() {
        long startTime = System.nanoTime();
        taskDistributionService.startDistributing(LocalDateTime.now());
        long endTime = System.nanoTime();
        return ResponseEntity.ok("Задачи успешно распределены за " + ((endTime - startTime) / 1000000) / 1000 + "." + ((endTime - startTime) / 100000000) % 10 + " секунд.");
    }

    @GetMapping("/test")
    public ResponseEntity test(@RequestBody Long id) {
        return ResponseEntity.ok(taskDistributionService.getEmployeeTasksForToday(id));
//        return ResponseEntity.ok(new Date());
    }

}
