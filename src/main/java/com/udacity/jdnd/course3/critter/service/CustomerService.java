package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;



    private Customer convertDTOtoEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
//        customer.setPetIds(customerDTO.getPetIds());
//        List<Pet> pets = new ArrayList<>();
//        List<Long> petIds = customerDTO.getPetIds();
//        for (Long petId : petIds) {
//            pets.add(petService.convertDTOtoEntity(petService.getPetById(petId)));
//        }
//        customer.setPets(customerDTO.getPetIds());
        return customer;
    }

    private CustomerDTO convertEntitytoDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
//        customerDTO.setPetIds(customer.getPetIds());
//        List<Long> petIds = new ArrayList<>();
//        List<Pet> pets = customer.getPets();
//        for (Pet pet : pets) {
//            petIds.add(pet.getId());
//        }
//        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(convertDTOtoEntity(customerDTO));
        return convertEntitytoDTO(customer);
    }

    public List<CustomerDTO> findAllCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer c : customers) {
            allCustomers.add(convertEntitytoDTO(c));
        }
        return allCustomers;
    }

    public CustomerDTO getCustomerById(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            return convertEntitytoDTO(customerRepository.findById(id).get());
        } else {
            throw new NullPointerException("Customer not found");
        }
    }
}
