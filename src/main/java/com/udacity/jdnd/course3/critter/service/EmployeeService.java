package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            return employeeRepository.findById(id).get();
        } else {
            throw new NullPointerException("Employee not found");
        }
    }

    public List<Employee> findAllEmployees() {
        Iterable<Employee> employees = employeeRepository.findAll();
        List<Employee> allEmployees = new ArrayList<>();
        for (Employee e : employees) {
            allEmployees.add(e);
        }
        return allEmployees;
    }

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Set<DayOfWeek> weekday = new HashSet<>();
        weekday.add(dayOfWeek);
        List<Employee> employees = findAllEmployees();
        List<Employee> availableEmployees = new ArrayList<>();
        for(Employee employee: employees){
            if(employee.getSkills().containsAll(skills) && employee.getDaysAvailable().containsAll(weekday)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }
}
