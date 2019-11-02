package dependency.sample.service;

import dependency.sample.entity.Pet;
import dependency.sample.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet createPet(Pet transientPet) {
        Pet persistentPet = petRepository.save(transientPet);
        System.out.println(persistentPet.toString());
        return persistentPet;
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public List<Pet> getByName(String name) {
        return petRepository.findByNameContaining(name);
    }
}
