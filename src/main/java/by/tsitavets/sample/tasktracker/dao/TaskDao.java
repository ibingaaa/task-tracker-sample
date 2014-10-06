package by.tsitavets.sample.tasktracker.dao;

import by.tsitavets.sample.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDao extends JpaRepository<Task, Long> {
    List<Task> findByDeveloperId(Long id);
}
