/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.vet;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mathi
 */
@XmlRootElement
public class Specialties {

    private List<Specialty> specs;

    @XmlElement
    public List<Specialty> getSpecList() {
        if (specs == null) {
            specs = new ArrayList<>();
        }
        return specs;
    }

}
