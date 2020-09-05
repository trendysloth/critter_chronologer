package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOtoSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return convertScheduletoDTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAllSchedules();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s: schedules) {
            scheduleDTOs.add(convertScheduletoDTO(s));
        }
        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        List<Schedule> schedules = scheduleService.findSchedulesByPet(pet);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s: schedules) {
            scheduleDTOs.add(convertScheduletoDTO(s));
        }
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<Schedule> schedules = scheduleService.findSchedulesByEmployee(employee);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s: schedules) {
            scheduleDTOs.add(convertScheduletoDTO(s));
        }
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Schedule> schedules = scheduleService.findSchedulesByCustomer(customer);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s: schedules) {
            scheduleDTOs.add(convertScheduletoDTO(s));
        }
        return scheduleDTOs;
    }

    public Schedule convertDTOtoSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        List<Employee> employees = new ArrayList<>();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        for (Long employeeId: employeeIds) {
            employees.add(employeeService.getEmployeeById(employeeId));
        }
        schedule.setEmployees(employees);
        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = scheduleDTO.getPetIds();
        for (Long petId: petIds) {
            pets.add(petService.getPetById(petId));
        }
        schedule.setPets(pets);
        return schedule;
    }

    public ScheduleDTO convertScheduletoDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = schedule.getPets();
        for (Pet pet: pets) {
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);
        List<Long> employeeIds = new ArrayList<>();
        List<Employee> employees = schedule.getEmployees();
        for (Employee employee: employees) {
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }
}
