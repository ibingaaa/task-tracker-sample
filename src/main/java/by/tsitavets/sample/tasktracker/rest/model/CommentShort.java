package by.tsitavets.sample.tasktracker.rest.model;

import by.tsitavets.sample.tasktracker.model.Comment;
import by.tsitavets.sample.tasktracker.model.User;

import java.util.Date;

/**
 * Created by sequence on 25.09.2014.
 */
public class CommentShort {
    private Long id;
    private Long userId;
    private String text;
    private String userName;
    private Date creationTime;

    // CONSTRUCTORS ====================================================================================================
    public CommentShort() {

    }

    public CommentShort(Comment comment) {
        this.id = comment.getId();
        this.userId =  comment.getCreator().getId();
        this.text = comment.getText();
        this.userName = comment.getCreator().getName();
        this.creationTime = comment.getCreationTime();
    }

    // TRANSFORM METHODS ===============================================================================================
    public Comment toComment(User creator) {
        return new Comment(text, creator);
    }

    public Comment toComment(Comment comment) {
        if(text != null && !text.equals("")) comment.setText(text);
        return comment;
    }

    // GETTERS & SETTERS ===============================================================================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
