package ru.lct.itmoteam.taskservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lct.itmoteam.taskservice.entity.EmployeeEntity;
import ru.lct.itmoteam.taskservice.entity.Grade;
import ru.lct.itmoteam.taskservice.entity.TaskDistributionStatus;
import ru.lct.itmoteam.taskservice.entity.TaskEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepo  extends CrudRepository<TaskEntity, Long> {
    @Query("select (count(t) > 0) from TaskEntity t where t.type.id = ?1 and t.point.id = ?2 and t.completed = false")
    boolean existsByType_IdAndPoint_Id(Long typeId, Long pointId);

    @Query("select t from TaskEntity t where t.status = 'NOT_DISTRIBUTED' order by t.type.priority DESC, t.assignmentDate")
    List<TaskEntity> findByStatusOrderByType_PriorityDescAssignmentDateAsc();
    @Query("select t from TaskEntity t order by t.type.priority DESC")
    List<TaskEntity> findByOrderByType_PriorityDesc();
    @Query("""
            select count(t) from TaskEntity t
            where t.employee.id = ?1 and t.type.grade = ?2 and t.completionDatetime between ?3 and ?4""")
    long countByEmployee_IdAndType_GradeAndCompletionDatetimeBetween(Long id, Grade grade, LocalDateTime completionDatetimeStart, LocalDateTime completionDatetimeEnd);
    @Query("select count(t) from TaskEntity t where t.employee.id = ?1 and t.completed = false")
    long countByEmployee_IdAndCompletedFalse(Long id);
    @Query("select count(t) from TaskEntity t where t.employee.id = ?1 and t.type.grade = ?2 and t.completed = true")
    long countByEmployee_IdAndType_GradeAndCompletedTrue(Long id, Grade grade);
    @Query("select t from TaskEntity t where t.employee.id = ?1")
    List<TaskEntity> findAllFinishedTasksByEmployee(Long id);
    @Transactional
    @Modifying
    @Query("update TaskEntity t set t.completed = true, t.completionDatetime = ?1 where t.id = ?2")
    int updateCompletedAndCompletionDatetimeById(LocalDateTime completionDatetime, Long id);
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
