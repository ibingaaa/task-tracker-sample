package by.tsitavets.sample.tasktracker.dao;

import by.tsitavets.sample.tasktracker.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long>{
}
