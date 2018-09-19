/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mathi
 */
public interface PetTypeRepository extends CrudRepository<PetType, Integer> {
    
    @Transactional(readOnly = true)
    @Override
    Collection<PetType> findAll() throws DataAccessException;
}
