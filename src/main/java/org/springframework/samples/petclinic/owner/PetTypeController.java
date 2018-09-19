/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.owner;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.Vets;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mathi
 */
@Controller
public class PetTypeController {
    private final PetTypeRepository petTypes;
    
    public PetTypeController(PetTypeRepository petTypes) {
        this.petTypes = petTypes;
    }
    
    @GetMapping("/petTypes.html")
    public String displayPetTypes(Map<String, Object> model) {
       PetTypes types = new PetTypes();
       PetType type = new PetType();
       types.getTypeList().addAll(this.petTypes.findAll());
       model.put("types", types);
       model.put("petType", type);
       return "petTypes/petTypesList";
    }
    
    @PostMapping("/petTypes.html")
    public String saveType(@Valid PetType type, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/petTypes.html";
        }
        this.petTypes.save(type);
        return "redirect:/petTypes.html";
    }
    
    @GetMapping("/petTypes/{typeId}")
    public ModelAndView petTypesDetails(@PathVariable("typeId") int typeId) {
        ModelAndView mav = new ModelAndView("petTypes/petTypesDetails");
        mav.addObject("type", this.petTypes.findById(typeId).orElse(new PetType()));
        return mav;
    }
    
    @PostMapping("/petTypes/{typeId}")
    public String petTypesEdit(@Valid PetType type, BindingResult result, @PathVariable("typeId") int typeId) {
        if (result.hasErrors()) {
            return "redirect:/petTypes/" + typeId;
        }
        type.setId(typeId);
        this.petTypes.save(type);
        return "redirect:/petTypes.html";
    }
     
}
