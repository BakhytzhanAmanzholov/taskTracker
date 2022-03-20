package kz.bakhytzhan.security.controllers;

import kz.bakhytzhan.security.models.Person;
import kz.bakhytzhan.security.models.Project;
import kz.bakhytzhan.security.models.Task;
import kz.bakhytzhan.security.services.ProjectService;
import kz.bakhytzhan.security.services.TaskService;
import kz.bakhytzhan.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
// With this REST Controller we can create/read/modify/delete task and project data,
// we can also see the task sheet by project and the project sheet by user
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    public String isLogged(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if(!currentPrincipalName.equals("anonymousUser")){
            return currentPrincipalName;
        }
        return "anonymousUser";
    }
    @PostMapping("/project")
    public Project createProject(@RequestBody Project project){
        String currentUser = isLogged();
        Person person = userService.getUser(currentUser);
        project.setPerson(person);
        return projectService.saveProject(project);
    }
    @GetMapping("/project")
    public Project getProject(@RequestBody int id){
        return projectService.getProject((long) id);
    }
    @DeleteMapping("/project")
    public void deleteProject(@RequestBody int id){
        projectService.deleteProject((long) id);
        return;
    }
    @PutMapping("/project")
    public Project editProject(@RequestBody Project project){
        Project projectUpdating = projectService.getProject(project.getId());
        Collection<Task> tasks = taskService.findByProject(projectUpdating);
        for (Task task: tasks) {
            taskService.deleteTask(task.getId());

        }
        project.setPerson(projectUpdating.getPerson());
        Project projectAfterUpdating =  projectService.editProject(project);
        for (Task task: tasks) {
            task.setProject(projectAfterUpdating);
            taskService.saveTask(task);
        }
        return projectAfterUpdating;
    }
    @PostMapping("/task/{id}")
    public Task createTask(@PathVariable("id") int id, @RequestBody Task task){
        Project project = projectService.getProject((long) id);
        task.setProject(project);
        return taskService.saveTask(task);
    }
    @GetMapping("/task")
    public Task getTask(@RequestBody Long id){
        return taskService.getTask((long) id);
    }
    @DeleteMapping("/task")
    public void deleteTask(@RequestBody int id){
        taskService.deleteTask((long) id);
        return;
    }
   @PutMapping("/task")
    public Task editTask(@RequestBody Task task){
        Task taskUpdating = taskService.getTask(task.getId());
        task.setProject(taskUpdating.getProject());
        task.setId(taskUpdating.getId());
        return taskService.editTask(task);
    }
    @GetMapping("/listTask")
    public Collection<Task> listTask(@RequestBody Long id){
        Project project = projectService.getProject(id);
        return taskService.findByProject(project);
    }
    @GetMapping("/listProject")
    public Collection<Project> listProject(@RequestBody Long id){
        Person person = userService.getUserById(id);
        return projectService.findByUser(person);
    }

}
