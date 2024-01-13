package com.example.react_native_spring_ex_140113.repository;

import com.example.react_native_spring_ex_140113.entity.Board;
import com.example.react_native_spring_ex_140113.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByContentContaining(String keyword);
    List<Comment> findByBoard(Board board);
}
