package kz.bakhytzhan.security.repositories;

import kz.bakhytzhan.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositories extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
