package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.lct.itmoteam.taskservice.DTO.Point;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.service.PointService;

@Controller
@RequestMapping("/points")
@CrossOrigin
public class PointController {
    @Autowired
    private PointService pointService;

    @GetMapping
    public ResponseEntity getAllPointsStatistic() {
        try {
            return ResponseEntity.ok(pointService.getAllPointsStatistic());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось.");
        }
    }

    @PostMapping("/add")
    public ResponseEntity addPoint(@RequestBody Point point) {
        try {
            return ResponseEntity.ok(pointService.addPoint(point));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось добавить точку.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPoint(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pointService.getPointById(id));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось получить точку.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePoint(@PathVariable Long id) {
        try {
            pointService.deletePointById(id);
            return ResponseEntity.ok("Успешно удалено.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось удалить.");
        }
    }


}
