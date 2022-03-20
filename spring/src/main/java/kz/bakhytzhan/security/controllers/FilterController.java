package kz.bakhytzhan.security.controllers;

import kz.bakhytzhan.security.models.*;
import kz.bakhytzhan.security.services.ProjectService;
import kz.bakhytzhan.security.services.TaskService;
import kz.bakhytzhan.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


// We need this REST Controller to filter and sort the project and tasks data by priority.
// Example: find tasks by project in some range, or find project by user in exactly value priority
@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
@Slf4j
public class FilterController {
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
    @GetMapping("/task/rangePriority")
    public Collection<Task> rangePriorityTask (@RequestBody Range range){
        Project project = projectService.getProject(range.getId());
        return taskService.rangePriority(range.getStartPriority(),
                range.getFinishPriority(), project);
    }
    @GetMapping("/task/orderByPriority")
    public Collection<Task> orderByPriority(@RequestBody int id){
        Project project = projectService.getProject((long) id);
        return taskService.orderByPriority(project);
    }
    @GetMapping("/task/exactPriority")
    public Collection<Task> exactPriority(@RequestBody ExactValue exactValue){
        Project project = projectService.getProject(exactValue.getId());
        return taskService.exactPriority(exactValue.getValue(), project);
    }
    @GetMapping("/task/startValue")
    public Collection<Task> startValue(@RequestBody ExactValue exactValue){
        Project project = projectService.getProject(exactValue.getId());
        return taskService.startValue(exactValue.getValue(), project);
    }
    @GetMapping("/task/endValue")
    public Collection<Task> endValue(@RequestBody ExactValue exactValue){
        Project project = projectService.getProject(exactValue.getId());
        return taskService.endValue(exactValue.getValue(), project);
    }
    @GetMapping("/project/rangePriority")
    public Collection<Project> rangePriorityProject (@RequestBody Range range){
        Person person = userService.getUserById(range.getId());
        return projectService.rangePriority(range.getStartPriority(),
                range.getFinishPriority(), person);
    }
    @GetMapping("/project/orderByPriority")
    public Collection<Project> orderByPriorityProject(@RequestBody int id){
        Person person = userService.getUserById((long) id);
        return projectService.orderByPriority(person);
    }
    @GetMapping("/project/exactPriority")
    public Collection<Project> exactPriorityProject(@RequestBody ExactValue exactValue){
        Person person = userService.getUserById(exactValue.getId());
        return projectService.exactPriority(exactValue.getValue(), person);
    }
    @GetMapping("/project/startValue")
    public Collection<Project> startValueProject(@RequestBody ExactValue exactValue){
        Person person = userService.getUserById(exactValue.getId());
        return projectService.startValue(exactValue.getValue(), person);
    }
    @GetMapping("/project/endValue")
    public Collection<Project> endValueProject(@RequestBody ExactValue exactValue){
        Person person = userService.getUserById(exactValue.getId());
        return projectService.endValue(exactValue.getValue(), person);
    }
}
