package kz.bakhytzhan.security.services;

import kz.bakhytzhan.security.models.Role;
import kz.bakhytzhan.security.models.Person;

import java.util.List;

public interface UserService {
    Person saveUser(Person user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    Person getUser(String email);
    List<Person>getUsers();
    Person getUserById(Long id);
}
