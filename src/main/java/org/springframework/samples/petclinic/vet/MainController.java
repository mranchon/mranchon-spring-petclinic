/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.samples.petclinic.system.AuthService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
class MainController {

    private final VetRepository vets;
    private final SpecialtyRepository specialties;
    private final AuthService authService;

    public MainController(VetRepository clinicService, SpecialtyRepository specService, AuthService authSvc) {
        this.vets = clinicService;
        this.specialties = specService;
        this.authService = authSvc;
    }

    @GetMapping("/vets.html")
    public String showVetList(Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        model.put("vets", vets);
        return "vets/vetList";
    }
    
    @GetMapping("/vets/new")
    public String initCreationForm(Map<String, Object> model) {
        Vet vet = new Vet();
        Collection<Specialty> specList = specialties.findAll();
        model.put("vet", vet);
        model.put("vetSpec", specList);
        return "vets/createVetForm";
    }
    
    @PostMapping("/vets/new")
    public String processUpdateOwnerForm(@Valid Vet vet, @RequestParam("specialties") Set<Specialty> specs, BindingResult result) {
        if (result.hasErrors()) {
            return "vets/createVetForm";
        } else {
            vet.setSpecialtiesInternal(specs);
            String pwd = authService.hashPassword(vet.getPassword());
            vet.setPassword(pwd);
            this.vets.save(vet);
            return "redirect:/vets.html";
        }
    }
    
    @GetMapping("/vets/{vetId}")
    public ModelAndView showVet(@PathVariable("vetId") int vetId) {
        ModelAndView mav = new ModelAndView("vets/vetDetails");
        mav.addObject("vet", this.vets.findById(vetId).orElse(new Vet()));
        return mav;
    }
    
    @GetMapping("/vets/{vetId}/edit")
    public String editVet(@PathVariable("vetId") int vetId, Model model) {
        Vet vet = this.vets.findById(vetId).orElse(new Vet());
        Collection<Specialty> specList = specialties.findAll();
        model.addAttribute("vet", vet);
        model.addAttribute("vetSpec", specList);
        return "vets/createVetForm";
    }

    @PostMapping("/vets/{vetId}/edit")
    public String saveEditVet(@Valid Vet vet, @RequestParam("specialties") Set<Specialty> specs,  BindingResult result, @PathVariable("vetId") int vetId) {
        if (result.hasErrors()) {
            return "vets/createVetForm";
        } else {
            vet.setId(vetId);
            vet.setSpecialtiesInternal(specs);
            String pwd = authService.hashPassword(vet.getPassword());
            vet.setPassword(pwd);
            this.vets.save(vet);
            return "redirect:/vets/{vetId}";
        }
    }
    
    @GetMapping("/specs.html")
    public String displaySpec(Map<String, Object> model) {
        Specialties specs = new Specialties();
        Specialty spec = new Specialty();
        specs.getSpecList().addAll(this.specialties.findAll());
        model.put("specs", specs);
        model.put("spec", spec);
        return "specs/specialtiesForm";
    }
    
    @PostMapping("/specs.html")
    public String addSpec(@Valid Specialty spec, BindingResult result) {
        if (result.hasErrors()) {
            return "specs/specialtiesForm";
        } else {
            this.specialties.save(spec);
            return "redirect:/specs.html";
        }
    }
    
    @GetMapping("/specs/{specId}")
    public ModelAndView editSpec(@PathVariable("specId") int specId) {
        ModelAndView mav = new ModelAndView("specs/specDetails");
        mav.addObject("spec", this.specialties.findById(specId).orElse(new Specialty()));
        return mav;
    }
    
    @PostMapping("/specs/{specId}")
    public String edit(@Valid Specialty spec, BindingResult result, @PathVariable("specId") int specId) {
        if (result.hasErrors()) {
            return "specs/{specId}";
        } else {
            spec.setId(specId);
            this.specialties.save(spec);
            return "redirect:/specs.html";  
        }
    }
    
    @DeleteMapping("/specs/{specId}")
    public String delete(@Valid Specialty spec, @PathVariable("specId") int specId) {
        this.specialties.deleteById(specId);
        return "redirect:/specs.html";
    }
    
}
