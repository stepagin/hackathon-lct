package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Task;
import ru.lct.itmoteam.taskservice.entity.*;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.repository.TaskRepo;
import ru.lct.itmoteam.taskservice.repository.TaskTypeRepo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private TaskTypeRepo taskTypeRepo;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PointService pointService;


    public TaskEntity addTask(Task task) throws BadInputDataException {
        if (!taskTypeRepo.existsByName(task.getName())) {
            throw new BadInputDataException("Не существует типа задачи с таким id");
        }
        TaskTypeEntity type = taskTypeRepo.findByName(task.getName());
        PointEntity point;
        if (task.getPointId() != null) {
            if (!pointService.existsById(task.getPointId())) {
                throw new BadInputDataException("Неверно указан id точки.");
            }
            point = pointService.getPointEntityById(task.getPointId());
        } else if (task.getAddress() != null) {
            if (!pointService.existsByAddress(task.getAddress()))
                throw new BadInputDataException("Неверно указан адрес задачи.");
            point = pointService.getPointByAddress(task.getAddress());
        } else {
            throw new BadInputDataException("Не указана точка задачи.");
        }
        try {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setType(type);
            taskEntity.setPoint(point);
            taskEntity.setCompleted(false);

            if (Objects.equals(task.getStatus(), TaskDistributionStatus.DISTRIBUTED_BY_MANAGER.toString())) {
                EmployeeEntity employee = employeeService.getEmployeeEntityById(task.getEmployeeId());
                if (!employee.getGrade().suitableForTask(type.getGrade()))
                    throw new BadInputDataException("Грейд сотрудника меньше, чем требуемый грейд задачи.");

                taskEntity.setStatus(TaskDistributionStatus.DISTRIBUTED_BY_MANAGER);
                taskEntity.setEmployee(employee);
                taskEntity.setAssignmentDate(new Date());
            } else {
                taskEntity.setStatus(TaskDistributionStatus.NOT_DISTRIBUTED);
            }
            return taskRepo.save(taskEntity);
        } catch (BadInputDataException e) {
            throw new BadInputDataException(e.getMessage());
        } catch (Exception e) {
            throw new BadInputDataException("Не удалось добавить задачу.");
        }
    }

    public List<TaskTypeEntity> getAllTaskTypes() throws EntityNotFoundException {
        try {
            Iterable<TaskTypeEntity> source = taskTypeRepo.findAll();
            return StreamSupport.stream(source.spliterator(), false)
                    .toList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Не удалось загрузить данные о типах задач.");
        }
    }

    public Task getTaskById(Long id) throws BadInputDataException {
        try {
            Optional<TaskEntity> task = taskRepo.findById(id);
            if (task.isEmpty())
                throw new BadInputDataException("Не найдена задача с таким id");
            return Task.toModel(task.get());
        } catch (Exception e) {
            throw new BadInputDataException("Не удалось достать задачу");
        }
    }

    public TaskEntity getTaskEntityById(Long id) throws BadInputDataException {
        try {
            Optional<TaskEntity> task = taskRepo.findById(id);
            if (task.isEmpty())
                throw new BadInputDataException("Не найдена задача с таким id");
            return task.get();
        } catch (Exception e) {
            throw new BadInputDataException("Не удалось достать задачу");
        }
    }

    public List<Task> getAllUnfinishedTasks() {
        List<TaskEntity> source = taskRepo.findAllUnfinishedTasks();
        return source.stream()
                .map(Task::toModel)
                .toList();
    }

    public List<Task> getAllFinishedTasks() {
        List<TaskEntity> source = taskRepo.findAllFinishedTasks();
        return source.stream()
                .map(Task::toModel)
                .toList();
    }

    public boolean existsTaskById(Long id) {
        return taskRepo.existsById(id);
    }


    public List<Task> getAllTasks() {
        Iterable<TaskEntity> source = taskRepo.findAll();
        return StreamSupport.stream(source.spliterator(), false)
                .map(Task::toModel)
                .toList();
    }

    public List<Task> getHistory(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return taskRepo.findBetweenDate(dateFrom, dateTo).stream()
                .map(Task::toModel)
                .toList();
    }

    public List<Task> getDistributedTasks() {
        return taskRepo.findAllDistributed().stream()
                .map(Task::toModel)
                .toList();
    }

    public List<Task> getAllEmployeeTasks(Long employeeId) {
        return taskRepo.findByEmployee_Id(employeeId).stream()
                .map(Task::toModel)
                .toList();
    }

    public Task assignTaskToEmployee(Long taskId, Long employeeId) throws BadInputDataException {
        if (!existsTaskById(taskId))
            throw new BadInputDataException("Не найдена задача с таким id.");
        EmployeeEntity employee = employeeService.getEmployeeEntityById(employeeId);
        TaskEntity task = getTaskEntityById(taskId);
        if (!employee.getGrade().suitableForTask(task.getType().getGrade())) {
            throw new BadInputDataException("Задача недоступна для сотрудника с таким грейдом.");
        }
        if (task.getEmployee() != null && task.getStatus() != TaskDistributionStatus.NOT_DISTRIBUTED) {
            throw new BadInputDataException("Задача уже распределена.");
        }
        if (task.isCompleted()) {
            throw new BadInputDataException("Невозможно перераспределить выполненную задачу.");
        }
        taskRepo.updateAssignment(TaskDistributionStatus.DISTRIBUTED_BY_MANAGER, employee, new Date(), taskId);
        return getTaskById(taskId);
    }

    public Task unassignTask(Long taskId) throws BadInputDataException {
        if (!taskRepo.existsById(taskId))
            throw new BadInputDataException("Не найдена задача с таким id.");
        taskRepo.updateAssignment(TaskDistributionStatus.NOT_DISTRIBUTED, null, null, taskId);
        return getTaskById(taskId);
    }
}
