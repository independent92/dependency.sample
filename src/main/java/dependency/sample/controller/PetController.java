package dependency.sample.controller;

import dependency.sample.entity.Pet;
import dependency.sample.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping(value = "/create")
    public Pet create(@RequestBody Pet pet) {
        System.out.println("POST method: /pet is working, and has a body:" + pet.toString());
        return petService.createPet(pet);
    }

    @GetMapping
    public List<Pet> getAll() {
        return petService.getAll();
    }

    @GetMapping(value = "/search")
    public List<Pet> getByName(@RequestParam String name) {
        return petService.getByName(name);
    }
}
