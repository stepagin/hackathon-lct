package ru.lct.itmoteam.taskservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lct.itmoteam.taskservice.service.ReportService;

@Controller
@RequestMapping("/report")
@CrossOrigin
public class ReportController {
    @Autowired
    private ReportService reportService;
    // TODO: /report?date=DATE
    // TODO: /report?date_from=DATEFROM&date_to=DATETO

}
