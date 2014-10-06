package by.tsitavets.sample.tasktracker.rest.model;

import by.tsitavets.sample.tasktracker.model.User;
import by.tsitavets.sample.tasktracker.model.enums.UserRole;

public class UserShort {
    private Long id;
    private String login;
    private String name;
    private String role;
    private String password;

    // CONSTRUCTORS ====================================================================================================
    public UserShort() {

    }

    public UserShort(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.name = user.getName();
        this.role = user.getRole().name();
    }

    // TRANSFORM METHODS ===============================================================================================
    public User toUser() {
        return new User(login, password, name, UserRole.valueOf(role));
    }

    public User toUser(User user) {
        if(login != null && !login.equals("")) user.setLogin(login);
        if(name != null && !name.equals("")) user.setName(name);
        if(role !=null && !role.equals("")) user.setRole(UserRole.valueOf(role));
        return user;
    }

    // GETTERS & SETTERS ===============================================================================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
