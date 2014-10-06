package by.tsitavets.sample.tasktracker.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Task> tasks;

    @Column(name = "creation_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private User creator;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "text", length = 65500, nullable = false)
    private String text;

    @Lob
    @Column(name = "description", length = 1500, nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_comments",
        joinColumns = @JoinColumn(name = "project"),
        inverseJoinColumns = @JoinColumn(name = "comment"))
    private List<Comment> comments;

    // CONSTRUCTORS ====================================================================================================
    public Project() {

    }

    public Project(String name, String text, String description, User creator) {
        this.name = name;
        this.text = text;
        this.description = description;
        this.creator = creator;
        this.creationTime = new Date();
    }

    // GETTERS & SETTERS ===============================================================================================
    public Long getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
