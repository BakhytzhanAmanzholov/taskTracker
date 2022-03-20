package kz.bakhytzhan.security.services;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.models.Project;
import kz.bakhytzhan.security.models.Task;

import java.util.Collection;
// This is our interface, here we prescribe our methods, after which the Implementation class uses them
public interface ProjectService {
    Project saveProject(Project project);
    Collection<Project> findByUser(Person person);
    Project getProject(Long id);
    Collection<Project> findAll();
    void deleteProject(Long id);
    Project editProject(Project project);

    Collection<Project> rangePriority(int start, int end, Person person);
    Collection<Project> orderByPriority(Person person);
    Collection<Project> exactPriority(int value, Person person);
    Collection<Project> startValue(int value, Person person);
    Collection<Project> endValue(int value, Person person);
}
