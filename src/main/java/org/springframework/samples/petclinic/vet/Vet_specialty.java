/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.vet;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

/**
 *
 * @author mathi
 */
@Entity
@Table(name = "vet_specialties")
public class Vet_specialty implements Serializable{
        
    @Id
    private Integer id;
    
    private Integer vet_id;
    
    @JoinTable(name = "specialties", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Integer specialty_id;
    
    public Vet_specialty() {};

    public Vet_specialty(Integer vetId, Integer specId) {
        this.vet_id = vetId;
        this.specialty_id = specId;
    }

    public Integer getVetId() {
        return vet_id;
    }

    public void setVetId(Integer vetId) {
        this.vet_id = vetId;
    }

    public Integer getSpecId() {
        return specialty_id;
    }

    public void setSpecId(Integer specId) {
        this.specialty_id = specId;
    }    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
