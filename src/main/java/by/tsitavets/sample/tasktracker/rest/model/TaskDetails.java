package by.tsitavets.sample.tasktracker.rest.model;

import by.tsitavets.sample.tasktracker.model.Project;
import by.tsitavets.sample.tasktracker.model.Task;
import by.tsitavets.sample.tasktracker.model.User;
import by.tsitavets.sample.tasktracker.model.enums.TaskStatus;

import java.util.Date;
import java.util.List;

public class TaskDetails {
    private Long id;
    private Long projectId;
    private Long creatorId;
    private Long developerId;
    private String name;
    private String description;
    private String creatorName;
    private String developerName;
    private String projectName;
    private Date creationTime;
    private String status;
    private List<CommentShort> comments;

    // CONSTRUCTORS ====================================================================================================
    public TaskDetails() {

    }

    public TaskDetails(Task task, List<CommentShort> comments) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus().name();

        this.projectId = task.getProject().getId();
        this.projectName = task.getProject().getName();

        this.creatorId = task.getCreator().getId();
        this.creatorName = task.getCreator().getName();
        this.creationTime = task.getCreationTime();

        this.comments = comments;

        if (task.getDeveloper() != null) {
            this.developerId = task.getDeveloper().getId();
            this.developerName = task.getDeveloper().getName();
        }
    }

    // TRANSFORM METHODS ===============================================================================================
    public Task toTask(User creator, Project project) {
        return new Task(name, description, creator, project);
    }

    public Task toTask(Task task, User developer) {
        if (name != null && !name.equals("")) task.setName(name);
        if (description != null && !description.equals("")) task.setDescription(description);
        if (status != null && !status.equals("")) task.setStatus(TaskStatus.valueOf(status));
        task.setDeveloper(developer);
        return task;
    }

    // GETTERS & SETTERS ===============================================================================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CommentShort> getComments() {
        return comments;
    }

    public void setComments(List<CommentShort> comments) {
        this.comments = comments;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
