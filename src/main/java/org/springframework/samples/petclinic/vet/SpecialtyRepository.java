/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mathi
 */
public interface SpecialtyRepository extends CrudRepository<Specialty, Integer> {
    
    @Transactional(readOnly = true)
    @Override
    Collection<Specialty> findAll() throws DataAccessException;
    
    Specialty findByName(Specialty specName);
    
    
}
