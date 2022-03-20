package kz.bakhytzhan.security.services;

import kz.bakhytzhan.security.models.Role;
import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.repositories.RoleRepositories;
import kz.bakhytzhan.security.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserImplementation implements UserService, UserDetailsService {
    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person user = userRepositories.findByEmail(s);
        if(user == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        else {
            log.info("User found {}", user.getEmail());

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities) ;
    }
    @Override
    public Person saveUser(Person user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepositories.findByName("ROLE_USER");
        user.getRoles().add(role);
        return userRepositories.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new Role {}", role.getName());
        return roleRepositories.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding Role {} to User {}", roleName, email);
        Person user = userRepositories.findByEmail(email);
        Role role = roleRepositories.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Person getUser(String email) {
        log.info("Get User by Email {}", email);
        Person person;

        return userRepositories.findByEmail(email);
    }

    @Override
    public List<Person> getUsers() {
        log.info("Get all Users");
        return userRepositories.findAll();
    }

    @Override
    public Person getUserById(Long id) {
        Optional<Person> person = userRepositories.findById(id);
        return person.orElseGet(Person:: new);
    }
}
