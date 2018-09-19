/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mathi
 */
@XmlRootElement
public class PetTypes {

    private List<PetType> types;

    @XmlElement
    public List<PetType> getTypeList() {
        if (types == null) {
            types = new ArrayList<>();
        }
        return types;
    }

}
