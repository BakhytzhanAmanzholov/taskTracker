package kz.bakhytzhan.security.repositories;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
// Here are our repositories.
// This interface performs the functions of working with a DBMS,
// the methods of this repositories are described below
public interface ProjectRepositories extends JpaRepository<Project, Long> {
    Optional<Project> findById(Long id);
    Collection<Project> findAllByPerson(Person person);
    void deleteById(Long id);
    Collection<Project> findAllByPersonOrderByPriority(Person person);
}
