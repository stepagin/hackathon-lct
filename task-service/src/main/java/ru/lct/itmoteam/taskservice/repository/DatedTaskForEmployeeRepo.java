package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lct.itmoteam.taskservice.entity.DatedTaskForEmployeeEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface DatedTaskForEmployeeRepo extends CrudRepository<DatedTaskForEmployeeEntity, Long> {
    @Query("""
            select d from DatedTaskForEmployeeEntity d
            where d.doingDate = ?1 and d.employeeId = ?2 and d.previousDatedTask is null""")
    DatedTaskForEmployeeEntity findByDoingDateAndEmployeeIdAndPreviousDatedTaskNull(Date doingDate, Long employeeId);
    @Query("select d from DatedTaskForEmployeeEntity d where d.doingDate = ?1 and d.employeeId = ?2")
    List<DatedTaskForEmployeeEntity> findAdllTasksForDateWhereEmployeeId(Date doingDate, Long employeeId);

    @Transactional
    @Modifying
    @Query("update DatedTaskForEmployeeEntity d set d.minutesToGo = ?1 where d.id = ?2")
    int updateMinutesToGoById(int minutesToGo, Long id);

    @Transactional
    @Modifying
    @Query("""
            update DatedTaskForEmployeeEntity d set d.previousDatedTask = ?1, d.nextDatedTask = ?2, d.minutesToGo = ?3
            where d.id = ?4""")
    int updatePreviousDatedTaskAndNextDatedTaskAndMinutesToGoById(Long previousDatedTask, Long nextDatedTask, int minutesToGo, Long id);


}
