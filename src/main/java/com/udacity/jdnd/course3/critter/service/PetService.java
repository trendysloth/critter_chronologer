package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet convertDTOtoEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setOwnerId(petDTO.getOwnerId());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        return pet;
    }

    public PetDTO convertEntitytoDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getOwnerId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = petRepository.save(convertDTOtoEntity(petDTO));
        return convertEntitytoDTO(pet);
    }

    public PetDTO getPetById(Long id) {
        if (petRepository.findById(id).isPresent()) {
            return convertEntitytoDTO(petRepository.findById(id).get());
        } else {
            throw new NullPointerException("Pet not found");
        }
    }
}
