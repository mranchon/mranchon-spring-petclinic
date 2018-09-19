/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.system;


import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathi
 */
@Service
public class AuthService {
    
    @Autowired
    private VetRepository vetRepo;
        
    public boolean findUser(String uname, String upwd) {
            System.out.println("Checking the user in the database");
            System.out.println("login: " + uname);
            System.out.println("pwd: " + upwd);
            boolean isValidUser = false;
            try {
                List<Vet> userObj = vetRepo.login(uname);
                Vet vet = userObj.get(0);
                if(!userObj.isEmpty() && checkPass(upwd, vet.getPassword())) {
                    isValidUser = true;
                }
            } catch(Exception e) {
                    System.out.println("An error occurred while fetching the user details from the database: " + e);	
            }
            return isValidUser;
    }
    
    public String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    
    private Boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
        
}
