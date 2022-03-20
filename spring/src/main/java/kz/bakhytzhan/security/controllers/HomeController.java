package kz.bakhytzhan.security.controllers;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// We need this REST Controller so that we can register
@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final UserService userService;

    @PostMapping("/registration")
    public Person registration(@RequestBody Person person){
        Person person1 =  userService.saveUser(person);
        userService.addRoleToUser(person.getEmail(), "ROLE_USER");
        return person1;
    }
}
