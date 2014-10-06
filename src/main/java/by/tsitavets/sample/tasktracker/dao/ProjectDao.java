package by.tsitavets.sample.tasktracker.dao;

import by.tsitavets.sample.tasktracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project, Long> {
}
