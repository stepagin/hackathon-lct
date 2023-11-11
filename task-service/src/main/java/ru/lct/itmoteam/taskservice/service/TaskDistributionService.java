package ru.lct.itmoteam.taskservice.service;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.PointStatistic;
import ru.lct.itmoteam.taskservice.DTO.Task;
import ru.lct.itmoteam.taskservice.entity.*;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.repository.ApplicationRepo;
import ru.lct.itmoteam.taskservice.repository.IssuanceRepo;
import ru.lct.itmoteam.taskservice.repository.TaskRepo;
import ru.lct.itmoteam.taskservice.repository.TaskTypeRepo;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskDistributionService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskTypeRepo taskTypeRepo;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PointService pointService;
    @Autowired
    private DatedTaskForEmployeeService datedTaskForEmployeeService;
    @Autowired
    private YandexMapService yandexMapService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private IssuanceRepo issuanceRepo;
    @Autowired
    private ApplicationRepo applicationRepo;

    public void startDistributing(LocalDateTime timeNow) {
        // выбирается дата распределения (если до 9:00 то сегодня, иначе завтра)
        Date currentDate;
        if (timeNow.getHour() <= 8) {
            currentDate = new Date();
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.add(Calendar.DAY_OF_YEAR, 1);
            currentDate = gc.getTime();
        }
        // генерятся задачи, которые только появились
        // распределяются задачи, предварительно отсортировавшись по приоритету
        generateTasks();
        // берутся сотрудники, которые is active
        List<EmployeeEntity> employeeEntityList = employeeService.getAllActiveEmployeeEntities();
        List<TaskEntity> taskEntityList = taskService.getAllUnassignedTaskEntitiesOrdered();
        try {
            EmployeeEntity employee;
            for (TaskEntity task : taskEntityList) {
                employee = getFirstSuitableEmployeeId(task, employeeEntityList);
                if (employee != null) {
                    Pair<Integer, Long> timePosition = getTimeAddedByTaskForEmployeeForDate(task, employee, currentDate);
                    assignTaskAndAutomaticallyOrder(task.getId(), employee, timePosition.b, timePosition.a, currentDate);
                }
            }
        } catch (BadInputDataException e) {
            throw new RuntimeException(e);
        }
    }


    private EmployeeEntity getFirstSuitableEmployeeId(TaskEntity task, List<EmployeeEntity> employeeEntityList) {
        // учитывается грейд и время дня
        if (task.getType().getGrade() == Grade.JUNIOR) {
            return getMinWorkingDay(employeeEntityList.stream()
                    .filter(employee -> getEmployeeWorkingDay(employee.getId()) + 90 <= 8 * 60)
                    .toList()
            );

        } else if (task.getType().getGrade() == Grade.MIDDLE) {
            return getMinWorkingDay(
                    employeeEntityList.stream()
                            .filter(employee -> employee.getGrade() != Grade.JUNIOR)
                            .filter(employee -> getEmployeeWorkingDay(employee.getId()) + 120 <= 8 * 60)
                            .toList()
            );
        } else if (task.getType().getGrade() == Grade.SENIOR) {
            return getMinWorkingDay(
                    employeeEntityList.stream()
                            .filter(employee -> employee.getGrade() == Grade.SENIOR)
                            .filter(employee -> getEmployeeWorkingDay(employee.getId()) + 4 * 60 <= 8 * 60)
                            .toList()
            );
        }
        return null;
    }

    private void generateTasks() {
        List<PointStatistic> pointStatistics = pointService.getAllPointsStatistic();
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(GregorianCalendar.DAY_OF_YEAR, -1);
        Date tomorrow = gc.getTime();
        gc.add(GregorianCalendar.DAY_OF_YEAR, -6);
        Date weekAgo = gc.getTime();
        gc.add(GregorianCalendar.DAY_OF_YEAR, -7);
        Date twoWeeksAgo = gc.getTime();
        for (PointStatistic i : pointStatistics) {
            if (!i.isMaterialsDelivered() || !pointService.getPointEntityById(i.getId()).getJoinDate().before(tomorrow)) {
                Task task = new Task();
                task.setPointId(i.getId());
                task.setName("Доставка карт и материалов");
                try {
                    taskService.addTask(task);
                } catch (BadInputDataException ignored) {
                }
            } else if (i.getIssuanceCount() * 2 < i.getApplicationsCount()) {
                Task task = new Task();
                task.setPointId(i.getId());
                task.setName("Обучение агента");
                try {
                    taskService.addTask(task);
                } catch (BadInputDataException ignored) {
                }
            } else if (applicationRepo.countApplicationsFromDate(twoWeeksAgo) == 0 ||
                    applicationRepo.countApplicationsFromDate(weekAgo) > 0
                            && issuanceRepo.countIssuanceFromDate(weekAgo) == 0) {
                Task task = new Task();
                task.setPointId(i.getId());
                task.setName("Выезд на точку для стимулирования выдач");
                try {
                    taskService.addTask(task);
                } catch (BadInputDataException ignored) {
                }
            }
        }
    }

    private EmployeeEntity getMinWorkingDay(List<EmployeeEntity> employeeEntityList) {
        if (employeeEntityList.size() < 1)
            return null;
        int minDay = 10 * 60;
        EmployeeEntity minEmployee = employeeEntityList.get(0);
        for (EmployeeEntity i : employeeEntityList) {
            int bufDay = getEmployeeWorkingDay(i.getId());
            if (bufDay < minDay) {
                minDay = bufDay;
                minEmployee = i;
            }
        }
        return minEmployee;
    }

    public List<Task> getEmployeeTasksForDate(Long employeeId, Date date) throws BadInputDataException {
        DatedTaskForEmployeeEntity firstEl = datedTaskForEmployeeService.getFirstEmployeeTasksForDate(employeeId, date);
        List<Task> taskList = new ArrayList<>();
        if (firstEl == null) {
            return taskList;
        } else {
            DatedTaskForEmployeeEntity nowEl = firstEl;
            EmployeeEntity employeeEntity = employeeService.getEmployeeEntityById(employeeId);
            String startAddress = null;
            String finishAddress = employeeEntity.getLocation().getAddress();
            try {
                taskList.add(taskService.getTaskById(nowEl.getTaskId()));
            } catch (BadInputDataException ignored) {
            }
            while (nowEl.getNextDatedTask() != null) {
                startAddress = finishAddress;
                finishAddress = taskService.getTaskById(nowEl.getTaskId()).getAddress();
                datedTaskForEmployeeService.updateTaskMinutesById(nowEl.getId(),
                        yandexMapService.getMeanTimeBetweenTwoAddresses(startAddress, finishAddress));
                try {
                    taskList.add(taskService.getTaskById(nowEl.getTaskId()));
                } catch (BadInputDataException ignored) {
                }
                nowEl = getNext(nowEl);
            }
        }
        return taskList;
    }


    public List<Task> getEmployeeTasksForToday(Long id) {
        try {
            return getEmployeeTasksForDate(id, new Date());
        } catch (BadInputDataException e) {
            throw new RuntimeException(e);
        }
    }

    private DatedTaskForEmployeeEntity getNext(DatedTaskForEmployeeEntity now) {
        return datedTaskForEmployeeService.getById(now.getNextDatedTask());
    }

    public int getEmployeeWorkingDay(Long employeeId) {
        try {
            List<Task> taskList = getEmployeeTasksForToday(employeeId);
            String startAddress = employeeService.getEmployeeEntityById(employeeId).getLocation().getAddress();
            int result = 0;
            for (Task i :
                    taskList) {
                result += yandexMapService.getMeanTimeBetweenTwoAddresses(startAddress, i.getAddress());
                result += i.getMinutesToResolve();
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Pair<Integer, Long> getTimeAddedByTaskForEmployeeForDate(TaskEntity task, EmployeeEntity employee, Date date) throws BadInputDataException {
        DatedTaskForEmployeeEntity nowEl = datedTaskForEmployeeService.getFirstEmployeeTasksForDate(employee.getId(), date);
        if (nowEl == null) {
            return new Pair<>(yandexMapService.getMeanTimeBetweenTwoAddresses(
                    task.getPoint().getAddress(),
                    employee.getLocation().getAddress()),
                    null
            );
        }
        List<Task> taskList = getEmployeeTasksForToday(employee.getId());
        int minResult = nowEl.getMinutesToGo();
        DatedTaskForEmployeeEntity minPosition = nowEl;
        String address1,
                address2 = taskService.getTaskById(nowEl.getTaskId()).getAddress(),
                address3 = employee.getLocation().getAddress();
        while (nowEl.getNextDatedTask() != null) {
            address1 = address3;
            address3 = taskService.getTaskById(nowEl.getTaskId()).getAddress();
            if (getMinutes(address1, address2, address3) < minResult) {
                minResult = getMinutes(address1, address2, address3);
                minPosition = nowEl;
            }
            nowEl = getNext(nowEl);
        }
        return new Pair<>(minResult, minPosition.getId()); // минимальное время, позиция перед каким taskId
    }

    private int getMinutes(String address1, String address2, String address3) {
        return yandexMapService.getMeanTimeBetweenTwoAddresses(address1, address2)
                + yandexMapService.getMeanTimeBetweenTwoAddresses(address2, address3);
    }


    private void assignTaskAndAutomaticallyOrder(Long taskId, EmployeeEntity employee, Long position, int minutesToGo, Date currentDate) throws BadInputDataException {
        DatedTaskForEmployeeEntity DTDEM = new DatedTaskForEmployeeEntity();
        DTDEM.setEmployeeId(employee.getId());
        DTDEM.setMinutesToGo(minutesToGo);
        DTDEM.setDoingDate(currentDate);
        DTDEM.setTaskId(taskId);
        Long pos1 = null;
        if (position != null) {
            DTDEM.setNextDatedTask(position);
            Long pos3 = position;
            DTDEM.setPreviousDatedTask(datedTaskForEmployeeService.getById(position).getPreviousDatedTask());
            pos1 = datedTaskForEmployeeService.getById(position).getPreviousDatedTask();
            DatedTaskForEmployeeEntity savedDTDEM = datedTaskForEmployeeService.addTask(DTDEM);

            Long pos2 = savedDTDEM.getId();
            if (pos1 != null) {
                datedTaskForEmployeeService.updateOrderTask(pos1,
                        datedTaskForEmployeeService.getById(pos1).getPreviousDatedTask(),
                        pos2,
                        datedTaskForEmployeeService.getById(pos1).getMinutesToGo()
                );
            }
            datedTaskForEmployeeService.updateOrderTask(pos3,
                    pos2,
                    datedTaskForEmployeeService.getById(pos3).getNextDatedTask(),
                    minutesToGo
            );

        } else {
            DTDEM.setNextDatedTask(null);
            DatedTaskForEmployeeEntity savedDTDEM = datedTaskForEmployeeService.addTask(DTDEM);

        }

        taskRepo.updateAssignment(TaskDistributionStatus.DISTRIBUTED_AUTOMATICALLY, employee, new Date(), taskId);
    }
}
