package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Employee;
import ru.lct.itmoteam.taskservice.entity.EmployeeEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.exception.EntityNotFoundException;
import ru.lct.itmoteam.taskservice.repository.EmployeeRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private LocationService locationService;

    public EmployeeEntity addEmployee(EmployeeEntity employee) throws BadInputDataException {
        if (employee.getGrade() == null) {
            throw new BadInputDataException("Грейд сотрудника указан неверно. Грейд может быть: JUNIOR, MIDDLE, SENIOR или null.");
        }
        if (!locationService.existsByAddress(employee.getLocation().getAddress())) {
            throw new BadInputDataException("Офиса с таким адресом не существует.");
        }
        try {
            return employeeRepo.save(employee);
        } catch (Exception e) {
            throw new BadInputDataException("Не удалось добавить сотрудника в базу данных.");
        }
    }

    public List<Employee> getAllEmployees() {
        Iterable<EmployeeEntity> source = employeeRepo.findAll();
        return StreamSupport.stream(source.spliterator(), false).map(Employee::toModel).toList();
    }

    public Employee getEmployeeById(Long id) throws EntityNotFoundException {
        Optional<EmployeeEntity> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return Employee.toModel(employee.get());
        }
        throw new EntityNotFoundException("Пользователь с таким id не найден");
    }

    // TODO: List<Employee> getAllActiveEmployees()
}
