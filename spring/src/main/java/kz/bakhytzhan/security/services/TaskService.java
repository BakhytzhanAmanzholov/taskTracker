package kz.bakhytzhan.security.services;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.models.Project;
import kz.bakhytzhan.security.models.Task;

import java.util.Collection;

public interface TaskService {
    Task saveTask(Task task);
    Task getTask(Long id);
    Collection<Task> findByProject(Project project);
    void deleteTask(Long id);
    Task editTask(Task task);
    void updateProject(Task task);
    Collection<Task> rangePriority(int start, int end, Project project);
    Collection<Task> orderByPriority(Project project);
    Collection<Task> exactPriority(int value, Project project);
    Collection<Task> startValue(int value, Project project);
    Collection<Task> endValue(int value, Project project);

}
