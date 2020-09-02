package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertDTOtoPet(petDTO);
        return convertPettoDTO(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPettoDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        for(Pet p: pets) {
            petDTOs.add(convertPettoDTO(p));
        }
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.getCustomerById(ownerId);
        List<Pet> pets = customer.getPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet pet: pets) {
            petDTOs.add(convertPettoDTO(pet));
        }
        return petDTOs;
    }

    public Pet convertDTOtoPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        Long ownerId = petDTO.getOwnerId();
        if (ownerId != null) {
            Customer customer = customerService.getCustomerById(ownerId);
            pet.setCustomer(customer);
        }
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        return pet;
    }

    public PetDTO convertPettoDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        Long ownerId = pet.getCustomer().getId();
        petDTO.setOwnerId(ownerId);
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }

}
