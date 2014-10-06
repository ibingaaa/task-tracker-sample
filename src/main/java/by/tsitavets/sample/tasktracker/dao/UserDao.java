package by.tsitavets.sample.tasktracker.dao;

import by.tsitavets.sample.tasktracker.model.User;
import by.tsitavets.sample.tasktracker.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByLogin(String login);

    List<User> findByRole(UserRole role);
}
