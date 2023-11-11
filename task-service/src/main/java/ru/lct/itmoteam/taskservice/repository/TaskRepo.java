package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lct.itmoteam.taskservice.entity.EmployeeEntity;
import ru.lct.itmoteam.taskservice.entity.TaskDistributionStatus;
import ru.lct.itmoteam.taskservice.entity.TaskEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepo  extends CrudRepository<TaskEntity, Long> {
    @Transactional
    @Modifying
    @Query("update TaskEntity t set t.status = ?1, t.employee = ?2, t.assignmentDate = ?3 where t.id = ?4")
    int updateAssignment(TaskDistributionStatus status, EmployeeEntity employee, Date assignmentDate, Long id);

    @Transactional
    @Modifying
    @Query("update TaskEntity t set t.employee = ?2 where t.id = ?1")
    int updateEmployeeById(Long id, EmployeeEntity employee);
    List<TaskEntity> findByEmployee_Id(Long id);
    @Query("select t from TaskEntity t where t.status <> 'NOT_DISTRIBUTED' order by t.assignmentDate")
    List<TaskEntity> findAllDistributed();
    @Query("""
            select t from TaskEntity t
            where t.completed = true
            order by t.completed, t.assignmentDate, t.completionDatetime""")
    List<TaskEntity> findAllCompleted();
    @Query("""
            select t from TaskEntity t
            where t.completionDatetime >= ?1 and t.completionDatetime <= ?2
            order by t.completionDatetime""")
    List<TaskEntity> findBetweenDate(LocalDateTime from, LocalDateTime to);
    @Query("select t from TaskEntity t where t.completed = false order by t.assignmentDate")
    List<TaskEntity> findAllUnfinishedTasks();
    @Query("select t from TaskEntity t where t.completed = true order by t.assignmentDate")
    List<TaskEntity> findAllFinishedTasks();

}
