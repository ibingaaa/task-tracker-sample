package by.tsitavets.sample.tasktracker.rest.service;

import by.tsitavets.sample.tasktracker.dao.CommentDao;
import by.tsitavets.sample.tasktracker.dao.ProjectDao;
import by.tsitavets.sample.tasktracker.dao.TaskDao;
import by.tsitavets.sample.tasktracker.dao.UserDao;
import by.tsitavets.sample.tasktracker.model.Comment;
import by.tsitavets.sample.tasktracker.model.Project;
import by.tsitavets.sample.tasktracker.model.Task;
import by.tsitavets.sample.tasktracker.model.User;
import by.tsitavets.sample.tasktracker.model.enums.UserRole;
import by.tsitavets.sample.tasktracker.rest.ForbiddenException;
import by.tsitavets.sample.tasktracker.rest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RestServiceImpl implements RestService {
    // DAO =============================================================================================================
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    // PRIVATE =========================================================================================================
    @Autowired
    private BCryptPasswordEncoder encoder;

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userDao.findByLogin(userDetails.getUsername());
    }

    // REST SERVICE INTERFACE IMPLEMENTATION ===========================================================================
    // USERS ===========================================================================================================
    public void createUser(UserShort userShort) {
        User user = userShort.toUser();
        String encPass = encoder.encode(user.getPassword());
        user.setPassword(encPass);
        userDao.save(user);
    }

    public UserShort getUser() {
        User user = getAuthenticatedUser();
        return new UserShort(user);
    }

    public List<UserShort> listDevelopers() {
        List<User> users = userDao.findByRole(UserRole.Developer);
        List<UserShort> userShortList = new ArrayList<>();

        for (User user : users) {
            userShortList.add(new UserShort(user));
        }
        return userShortList;
    }

    public List<TaskShort> listDevelopersTasks() {
        User user = getAuthenticatedUser();
        List<Task> tasks = taskDao.findByDeveloperId(user.getId());
        List<TaskShort> shortTasks = new ArrayList<>();

        for (Task task : tasks) {
            shortTasks.add(new TaskShort(task));
        }

        return shortTasks;
    }

    // PROJECTS ========================================================================================================
    public List<ProjectShort> listProjects() {
        List<Project> projects = projectDao.findAll();
        List<ProjectShort> shorts = new ArrayList<>();

        for (Project project : projects) {
            ProjectShort projectShort = new ProjectShort(project);
            shorts.add(projectShort);
        }

        return shorts;
    }

    public void createProject(ProjectDetails projectDetails) {
        User user = getAuthenticatedUser();
        Project project = projectDetails.toProject(user);
        projectDao.save(project);
    }

    public ProjectDetails getProject(Long id) {
        Project project = projectDao.findOne(id);

        List<TaskShort> tasks = new ArrayList<>();
        for (Task task : project.getTasks()) {
            tasks.add(new TaskShort(task));
        }

        List<CommentShort> comments = new ArrayList<>();
        for (Comment comment : project.getComments()) {
            comments.add(new CommentShort(comment));
        }

        return new ProjectDetails(project, tasks, comments);
    }

    public void saveProject(Long id, ProjectDetails projectDetails) {
        Project project = projectDao.findOne(id);
        projectDetails.toProject(project);
        projectDao.save(project);
    }

    public void deleteProject(Long id) {
        projectDao.delete(id);
    }

    // TASKS ===========================================================================================================
    public void createTask(Long projectId, TaskDetails taskDetails) {
        Project project = projectDao.findOne(projectId);
        User creator = getAuthenticatedUser();

        Task task = taskDetails.toTask(creator, project);
        taskDao.save(task);
    }

    public TaskDetails getTask(Long id) {
        Task task = taskDao.findOne(id);

        List<CommentShort> comments = new ArrayList<>();
        for (Comment comment : task.getComments()) {
            comments.add(new CommentShort(comment));
        }

        return new TaskDetails(task, comments);
    }

    public void saveTask(Long id, TaskDetails taskDetails) {
        Task task = taskDao.findOne(id);
        User developer = null;
        if (taskDetails.getDeveloperId() != null) developer = userDao.findOne(taskDetails.getDeveloperId());

        taskDetails.toTask(task, developer);
        taskDao.save(task);
    }

    public void deleteTask(Long id) {
        taskDao.delete(id);
    }

    // COMMENTS ========================================================================================================
    public void createProjectComment(Long projectId, CommentShort comment) {
        Project project = projectDao.findOne(projectId);
        User user = getAuthenticatedUser();

        Comment comm = comment.toComment(user);
        project.getComments().add(comm);

        projectDao.save(project);
    }

    public void createTaskComment(Long taskId, CommentShort comment) {
        Task task = taskDao.findOne(taskId);
        User creator = getAuthenticatedUser();

        Comment comm = comment.toComment(creator);
        task.getComments().add(comm);

        taskDao.save(task);
    }

    public void saveComment(Long commentId, CommentShort comment) {
        Comment comm = commentDao.findOne(commentId);
        User user = getAuthenticatedUser();

        if (user.getId() != comm.getCreator().getId()) throw new ForbiddenException();

        comment.toComment(comm);
        commentDao.save(comm);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentDao.findOne(commentId);
        User user = getAuthenticatedUser();

        if (user.getId() != comment.getCreator().getId()) throw new ForbiddenException();

        commentDao.delete(commentId);
    }
}