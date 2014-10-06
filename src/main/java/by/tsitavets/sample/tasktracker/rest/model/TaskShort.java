package by.tsitavets.sample.tasktracker.rest.model;

import by.tsitavets.sample.tasktracker.model.Task;
import by.tsitavets.sample.tasktracker.model.enums.TaskStatus;

import java.util.Date;

public class TaskShort {
    private long id;
    private String name;
    private TaskStatus status;
    private int commentCount;
    private Date creationTime;

    public TaskShort() {

    }

    public TaskShort(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.status = task.getStatus();
        this.commentCount = task.getComments().size();
        this.creationTime = task.getCreationTime();
    }

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
