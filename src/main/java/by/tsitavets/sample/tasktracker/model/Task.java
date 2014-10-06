package by.tsitavets.sample.tasktracker.model;

import by.tsitavets.sample.tasktracker.model.enums.TaskStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Lob
    @Column(name = "description", length = 65000, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 45, nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private User developer;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private User creator;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "task_comments",
        joinColumns = @JoinColumn(name = "task"),
        inverseJoinColumns = @JoinColumn(name = "comment"))
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "project_id", updatable = false)
    private Project project;

    // CONSTRUCTORS ====================================================================================================
    public Task() {

    }

    public Task(String name, String description, User creator, Project project) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.creationTime = new Date();
        this.status = TaskStatus.FREE;
    }

    // GETTERS & SETTERS ===============================================================================================
    public Long getId() {
        return id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
