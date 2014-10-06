package by.tsitavets.sample.tasktracker.rest.controller;

import by.tsitavets.sample.tasktracker.model.Comment;
import by.tsitavets.sample.tasktracker.rest.ForbiddenException;
import by.tsitavets.sample.tasktracker.rest.model.*;
import by.tsitavets.sample.tasktracker.rest.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestApiController {
    @Autowired
    private RestService service;

    // UNAUTHORIZED ====================================================================================================
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void unauthorized() {
    }

    @RequestMapping(value = "/login_successful")
    public UserShort login_successful() {
        return service.getUser();
    }

    // USERS ===========================================================================================================
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserShort getCurrentUser() {
        return service.getUser();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "User successfully created")
    public void createUser(@RequestBody UserShort userShort) {
        service.createUser(userShort);
    }

    @RequestMapping(value = "/users/role/developer", method = RequestMethod.GET)
    public List<UserShort> listDevelopers() {
        return service.listDevelopers();
    }

    @RequestMapping(value = "/user/tasks", method = RequestMethod.GET)
    public List<TaskShort> listDeveloperTasks() {
        return service.listDevelopersTasks();
    }

    // PROJECTS ========================================================================================================
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<ProjectShort> listProjects() {
        return service.listProjects();
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Project successfully created")
    public void createProject(@RequestBody ProjectDetails projectDetails) {
        service.createProject(projectDetails);
    }

    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    public ProjectDetails getProject(@PathVariable long projectId) {
        return service.getProject(projectId);
    }

    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.POST)
    public void saveProject(@PathVariable long projectId, @RequestBody ProjectDetails projectDetails) {
        service.saveProject(projectId, projectDetails);
    }

    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.DELETE)
    public void deleteProject(@PathVariable long projectId) {
        service.deleteProject(projectId);
    }

    @RequestMapping(value = "/projects/{projectId}/tasks", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Task successfully created")
    public void createTask(@PathVariable long projectId, @RequestBody TaskDetails taskDetails) {
        service.createTask(projectId, taskDetails);
    }

    // TASKS ===========================================================================================================
    @RequestMapping(value = "/tasks/{taskId}")
    public TaskDetails getTask(@PathVariable long taskId) {
        return service.getTask(taskId);
    }

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.POST)
    public void saveTask(@PathVariable long taskId, @RequestBody TaskDetails taskDetails) {
        service.saveTask(taskId, taskDetails);
    }

    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable long taskId) {
        service.deleteTask(taskId);
    }

    // COMMENTS ========================================================================================================
    @RequestMapping(value = "/projects/{projectId}/comments", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Comment successfully added to project")
    public void createProjectComment(@PathVariable long projectId, @RequestBody CommentShort comment) {
        service.createProjectComment(projectId, comment);
    }

    @RequestMapping(value = "/tasks/{taskId}/comments", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Comment successfully added to task")
    public void createTaskComment(@PathVariable long taskId, @RequestBody CommentShort comment) {
        service.createTaskComment(taskId, comment);
    }

    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.POST)
    public void saveComment(@PathVariable long commentId, @RequestBody CommentShort comment) {
        service.saveComment(commentId, comment);
    }

    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable long commentId) {
        service.deleteComment(commentId);
    }

    // EXCEPTION HANDLING ==============================================================================================
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private void dataConflict() {}

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private void forbidden() {}
}
