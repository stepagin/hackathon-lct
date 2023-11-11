package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.service.ReportService;

import java.util.Date;

@Controller
@RequestMapping("/report")
@CrossOrigin
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/employee")
    public ResponseEntity getReportAboutEmployee(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(reportService.makeReportByEmployeeId(id));
        } catch (BadInputDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка во время составления отчёта. " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getAllReports() {
        try {
            return ResponseEntity.ok(reportService.makeAllReports());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка во время составления отчёта.");
        }
    }

    @GetMapping("/date")
    public ResponseEntity getReportsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from, @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        try {
            return ResponseEntity.ok(reportService.makeReportsBetweenDate(from, to));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка во время составления отчёта.");
        }
    }
}
