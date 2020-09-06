package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        Customer customer = savedPet.getCustomer();
        List<Pet> pets = customer.getPets();
        pets.add(pet);
        customer.setPets(pets);
        return savedPet;
    }

    public Pet getPetById(Long id) {
        if (petRepository.findById(id).isPresent()) {
            return petRepository.findById(id).get();
        } else {
            throw new NullPointerException("Pet not found");
        }
    }

    public List<Pet> getAllPets() {
        Iterable<Pet> pets = petRepository.findAll();
        List<Pet> allPets = new ArrayList<>();
        for (Pet p : pets) {
            allPets.add(p);
        }
        return allPets;
    }
}
