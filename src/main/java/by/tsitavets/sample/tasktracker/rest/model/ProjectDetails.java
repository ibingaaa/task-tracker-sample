package by.tsitavets.sample.tasktracker.rest.model;

import by.tsitavets.sample.tasktracker.model.Project;
import by.tsitavets.sample.tasktracker.model.User;

import java.util.Date;
import java.util.List;

public class ProjectDetails {
    private Long id;
    private Long creatorId;
    private String name;
    private String text;
    private String description;
    private String creatorName;
    private Date creationTime;
    private List<TaskShort> tasks;
    private List<CommentShort> comments;

    // CONSTRUCTORS ====================================================================================================
    public ProjectDetails() {

    }

    public ProjectDetails(Project project, List<TaskShort> tasks, List<CommentShort> comments) {
        this.id = project.getId();
        this.name = project.getName();
        this.text = project.getText();
        this.description = project.getDescription();
        this.creatorName = project.getCreator().getName();
        this.creatorId = project.getCreator().getId();
        this.creationTime = project.getCreationTime();
        this.tasks = tasks;
        this.comments = comments;
    }

    // TRANSFORM METHODS ===============================================================================================
    public Project toProject(User user) {
        return new Project(name, text, description, user);
    }

    public Project toProject(Project project) {
        if(name != null && !name.equals("")) project.setName(name);
        if(text != null && !text.equals("")) project.setText(text);
        if(description != null && !description.equals("")) project.setDescription(description);
        return project;
    }

    // GETTERS & SETTERS ===============================================================================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskShort> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskShort> tasks) {
        this.tasks = tasks;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
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
}
