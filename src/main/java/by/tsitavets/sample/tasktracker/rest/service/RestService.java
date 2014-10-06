package by.tsitavets.sample.tasktracker.rest.service;

import by.tsitavets.sample.tasktracker.rest.model.*;
import java.util.List;

public interface RestService {
    // USERS ===========================================================================================================
    public void createUser(UserShort userShort);
    public UserShort getUser();
    public List<UserShort> listDevelopers();
    public List<TaskShort> listDevelopersTasks();

    // PROJECTS ========================================================================================================
    public List<ProjectShort> listProjects();
    public void createProject(ProjectDetails projectDetails);
    public ProjectDetails getProject(Long id);
    public void saveProject(Long id, ProjectDetails projectDetails);
    public void deleteProject(Long id);

    // TASKS ===========================================================================================================
    public void createTask(Long projectId, TaskDetails taskDetails);
    public TaskDetails getTask(Long id);
    public void saveTask(Long id, TaskDetails taskDetails);
    public void deleteTask(Long id);

    // COMMENTS ========================================================================================================
    public void createProjectComment(Long projectId, CommentShort comment);
    public void createTaskComment(Long taskId, CommentShort comment);
    public void saveComment(Long commentId, CommentShort comment);
    public void deleteComment(Long commentId);
}
