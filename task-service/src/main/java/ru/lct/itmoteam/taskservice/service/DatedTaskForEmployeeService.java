package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.entity.DatedTaskForEmployeeEntity;
import ru.lct.itmoteam.taskservice.repository.DatedTaskForEmployeeRepo;

import java.util.Date;
import java.util.List;

@Service
public class DatedTaskForEmployeeService {
    @Autowired
    private DatedTaskForEmployeeRepo datedTaskForEmployeeRepo;

    public List<DatedTaskForEmployeeEntity> getEmployeeTasksForDate(Long id, Date date) {
        return datedTaskForEmployeeRepo.findAdllTasksForDateWhereEmployeeId(date, id);
    }
    public DatedTaskForEmployeeEntity getFirstEmployeeTasksForDate(Long id, Date date) {
        return datedTaskForEmployeeRepo.findByDoingDateAndEmployeeIdAndPreviousDatedTaskNull(date, id);
    }

    public DatedTaskForEmployeeEntity getById(Long id) {
        if (id == null) return null;
        return datedTaskForEmployeeRepo.findById(id).orElse(null);
    }

    public void updateTaskMinutesById(Long id, int minutes) {
        datedTaskForEmployeeRepo.updateMinutesToGoById(minutes, id);
    }

    public void updateOrderTask(Long id, Long previous, Long next, int timeToGo) {
        datedTaskForEmployeeRepo.updatePreviousDatedTaskAndNextDatedTaskAndMinutesToGoById(previous, next, timeToGo, id);
    }

    public DatedTaskForEmployeeEntity addTask(DatedTaskForEmployeeEntity task) {
        return datedTaskForEmployeeRepo.save(task);
    }


}
