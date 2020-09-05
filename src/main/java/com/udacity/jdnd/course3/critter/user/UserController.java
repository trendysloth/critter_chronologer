package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.saveCustomer(convertDTOtoCustomer(customerDTO));
        return convertCustomertoDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        List<Customer> customers = customerService.findAllCustomers();
        for (Customer c: customers) {
            customerDTOs.add(convertCustomertoDTO(c));
        }
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPetById(petId);
        Long ownerId = pet.getCustomer().getId();
        Customer customer = customerService.getCustomerById(ownerId);
        return convertCustomertoDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOtoEmployee(employeeDTO);
        return convertEmployeetoDTO(employeeService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeetoDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employee = employeeService.findEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills());
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee e: employee) {
            employeeDTOs.add(convertEmployeetoDTO(e));
        }
        return employeeDTOs;
    }

    public Customer convertDTOtoCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            for (Long petId : petIds) {
                pets.add(petService.getPetById(petId));
            }
            customer.setPets(pets);
        } else {
            customer.setPets(new ArrayList<>());
        }
        return customer;
    }

    public CustomerDTO convertCustomertoDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();
        if (pets != null) {
            for (Pet pet : pets) {
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
        } else {
            customerDTO.setPetIds(new ArrayList<>());
        }
        return customerDTO;
    }

    private Employee convertDTOtoEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }

    private EmployeeDTO convertEmployeetoDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }
}
