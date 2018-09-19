package org.springframework.samples.petclinic.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Configuration
@ComponentScan("com.springframework.samples.petclinic")
public class WelcomeController {
    
    @Autowired
    private AuthService authenticateService;

    @GetMapping("/")
    public String displayLoginFrom() {
        return "login";
    }
    
    // Checks if the user credentials are valid or not.
    @PostMapping("/")
    public ModelAndView validateUsr(@RequestParam("username")String username, @RequestParam("password")String password) {
            String msg = "";
            boolean isValid = authenticateService.findUser(username, password);
            System.out.println("Is user valid?= " + isValid);

            if(isValid) {
                    msg = "Welcome " + username + "!";
                    return new ModelAndView("welcome", "output", msg);
            } else {
                    msg = "Invalid credentials";
                    return new ModelAndView("login", "output", msg);
            }
    }
}
