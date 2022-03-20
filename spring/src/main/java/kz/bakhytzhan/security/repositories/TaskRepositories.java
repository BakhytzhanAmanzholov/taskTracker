package kz.bakhytzhan.security.repositories;

import kz.bakhytzhan.security.models.Project;
import kz.bakhytzhan.security.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepositories extends JpaRepository<Task, Long> {
    Collection<Task> findAllByProject(Project project);
    Optional<Task> findById(Long id);
    Collection<Task> findAllByProjectOrderByPriority(Project project);
}
