package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllSchedules() {
        Iterable<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> allSchedules = new ArrayList<>();
        for (Schedule s : schedules) {
            allSchedules.add(s);
        }
        return allSchedules;
    }

    public List<Schedule> findSchedulesByPet(Pet pet) {
        List<Schedule> schedules = findAllSchedules();
        List<Schedule> availableSchedules = new ArrayList<>();
        Set<Pet> petSet = new HashSet<>();
        petSet.add(pet);
        for (Schedule s : schedules) {
            if (s.getPets().containsAll(petSet)) {
                availableSchedules.add(s);
            }
        }
        return availableSchedules;
    }

    public List<Schedule> findSchedulesByEmployee(Employee employee) {
        List<Schedule> schedules = findAllSchedules();
        List<Schedule> availableSchedules = new ArrayList<>();
        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(employee);
        for (Schedule s : schedules) {
            if (s.getEmployees().containsAll(employeeSet)) {
                availableSchedules.add(s);
            }
        }
        return availableSchedules;
    }

    public List<Schedule> findSchedulesByCustomer(Customer customer) {
        List<Pet> pets = customer.getPets();
        List<Schedule> availableSchedules = new ArrayList<>();
        for (Pet p:pets) {
            List<Schedule> schedules = findSchedulesByPet(p);
            for (Schedule s: schedules) {
                if (!availableSchedules.contains(s)) {
                    availableSchedules.add(s);
                }
            }
        }
        return availableSchedules;
    }
}