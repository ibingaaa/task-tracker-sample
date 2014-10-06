package by.tsitavets.sample.tasktracker.rest.model;

import by.tsitavets.sample.tasktracker.model.Project;

import java.util.Date;

public class ProjectShort {
    private long id;
    private String name;
    private String description;
    private int taskCount;
    private int commentCount;
    private String creatorName;
    private Date creationTime;

    // CONSTRUCTORS ====================================================================================================
    public ProjectShort() {

    }

    public ProjectShort(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.taskCount = project.getTasks().size();
        this.commentCount = project.getComments().size();
        this.creatorName = project.getCreator().getName();
        this.creationTime = project.getCreationTime();
    }

    // GETTERS & SETTERS ===============================================================================================
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
