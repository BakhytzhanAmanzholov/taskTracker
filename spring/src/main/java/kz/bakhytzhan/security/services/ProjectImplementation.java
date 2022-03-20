package kz.bakhytzhan.security.services;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.models.Project;
import kz.bakhytzhan.security.models.Task;
import kz.bakhytzhan.security.repositories.ProjectRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


// In this class, we prescribe the logic of working with models and repository with Project.
// To sort the data, I first took all the data and then sort them
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectImplementation implements ProjectService{
    private final ProjectRepositories projectRepositories;
    @Override
    public Project saveProject(Project project) {
        log.info("Save project {}", project.getTitle());
        return projectRepositories.save(project);
    }

    @Override
    public Collection<Project> findByUser(Person person) {
        log.info("Get all projects by user {}", person.getEmail());
        return projectRepositories.findAllByPerson(person);
    }

    @Override
    public Project getProject(Long id) {
        log.info("Get project by id {}", id);
        Optional<Project> project = projectRepositories.findById(id);
        return project.orElseGet(Project::new);
    }

    @Override
    public Collection<Project> findAll() {
        log.info("Get all projects");
        return projectRepositories.findAll();
    }

    @Override
    public void deleteProject(Long id) {
        log.info("Delete project by id {}", id);
        projectRepositories.deleteById(id);
        return;
    }

    @Override
    public Project editProject(Project project) {
        log.info("Edit project {}", project.getTitle());
        projectRepositories.deleteById(project.getId());
        return projectRepositories.save(project);
    }
    @Override
    public Collection<Project> rangePriority(int start, int end, Person person) {
        Collection<Project> projects = projectRepositories.findAllByPersonOrderByPriority(person);
        Collection<Project> neededProjects  = new ArrayList<>();
        for (Project project: projects) {
            if(project.getPriority() >= start && project.getPriority() <=end){
                neededProjects.add(project);
            }
        }
        return neededProjects;
    }

    @Override
    public Collection<Project> orderByPriority(Person person) {
        return projectRepositories.findAllByPersonOrderByPriority(person);
    }

    @Override
    public Collection<Project> exactPriority(int value, Person person) {
        Collection<Project> projects = projectRepositories.findAllByPersonOrderByPriority(person);
        Collection<Project> neededProjects  = new ArrayList<>();
        for (Project project: projects) {
            if(project.getPriority()==value){
                neededProjects.add(project);
            }
        }
        return neededProjects;
    }

    @Override
    public Collection<Project> startValue(int value, Person person) {
        Collection<Project> projects = projectRepositories.findAllByPersonOrderByPriority(person);
        Collection<Project> neededProjects  = new ArrayList<>();
        for (Project project: projects) {
            if(value <= project.getPriority()){
                neededProjects.add(project);
            }
        }
        return neededProjects;
    }

    @Override
    public Collection<Project> endValue(int value, Person person) {
        Collection<Project> projects = projectRepositories.findAllByPersonOrderByPriority(person);
        Collection<Project> neededProjects  = new ArrayList<>();
        for (Project project: projects) {
            if(value >= project.getPriority()){
                neededProjects.add(project);
            }
        }
        return neededProjects;
    }
}
