package kz.bakhytzhan.security;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.models.Project;
import kz.bakhytzhan.security.models.Role;
import kz.bakhytzhan.security.services.ProjectService;
import kz.bakhytzhan.security.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;


//This is our main startup class, here we use Bean Command Line to launch certain methods,
// and Bean PasswordEncoder so that we can encrypt passwords
@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner run(UserService userService, ProjectService projectService){
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            Person person = new Person(null, "Bakhytzhan", "Amanzholov",
                    "amanzholovbakhytzhan@gmail.com", "password23A",
                    null, new ArrayList<>());
            userService.saveUser(person);
            projectService.saveProject(new Project(null, "New Title", "Description project",person, new Date(),
                    new Date(), "status", 8));
        };
    }

}
