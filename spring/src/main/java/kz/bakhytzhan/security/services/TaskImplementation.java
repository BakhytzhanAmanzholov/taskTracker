package kz.bakhytzhan.security.services;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.models.Project;
import kz.bakhytzhan.security.models.Task;
import kz.bakhytzhan.security.repositories.TaskRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskImplementation implements TaskService{
    private final TaskRepositories taskRepositories;

    @Override
    public Task saveTask(Task task) {
        log.info("Save task {}", task.getTitle());
        return taskRepositories.save(task);
    }

    @Override
    public Task getTask(Long id) {
        log.info("Get task by id {} ", id);
        Optional<Task> task = taskRepositories.findById(id);
        return task.orElseGet(Task::new);
    }

    @Override
    public Collection<Task> findByProject(Project project) {
        log.info("Get tasks by project {}", project.getTitle());
        return taskRepositories.findAllByProject(project);
    }

    @Override
    public void deleteTask(Long id) {
        log.info("Delete task by id {}", id);
        taskRepositories.deleteById(id);
        return;
    }

    @Override
    public Task editTask(Task task) {
        log.info("Edit task {}", task.getTitle());
        taskRepositories.deleteById(task.getId());
        return taskRepositories.save(task);
    }

    @Override
    public void updateProject(Task task) {
        log.info("Update task {}", task.getTitle());
        taskRepositories.deleteById(task.getId());
        taskRepositories.save(task);
        return;
    }

    @Override
    public Collection<Task> rangePriority(int start, int end, Project project) {
        Collection<Task>  tasks = taskRepositories.findAllByProjectOrderByPriority(project);
        Collection<Task> neededTasks  = new ArrayList<>();
        for (Task task: tasks) {
            if(task.getPriority() >= start && task.getPriority() <=end){
                neededTasks.add(task);
            }
        }
        return neededTasks;
    }

    @Override
    public Collection<Task> orderByPriority(Project project) {
        return taskRepositories.findAllByProjectOrderByPriority(project);
    }

    @Override
    public Collection<Task> exactPriority(int value, Project project) {
        Collection<Task>  tasks = taskRepositories.findAllByProjectOrderByPriority(project);
        Collection<Task> neededTasks  = new ArrayList<>();
        for (Task task: tasks) {
           if(task.getPriority()==value){
               neededTasks.add(task);
           }
        }
        return neededTasks;
    }

    @Override
    public Collection<Task> startValue(int value, Project project) {
        Collection<Task>  tasks = taskRepositories.findAllByProjectOrderByPriority(project);
        Collection<Task> neededTasks  = new ArrayList<>();
        for (Task task: tasks) {
            if(value <= task.getPriority()){
                neededTasks.add(task);
            }
        }
        return neededTasks;
    }

    @Override
    public Collection<Task> endValue(int value, Project project) {
        Collection<Task>  tasks = taskRepositories.findAllByProjectOrderByPriority(project);
        Collection<Task> neededTasks  = new ArrayList<>();
        for (Task task: tasks) {
            if(value >= task.getPriority()){
                neededTasks.add(task);
            }
        }
        return neededTasks;
    }
}
