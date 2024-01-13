package com.example.react_native_spring_ex_140113.repository;

import com.example.react_native_spring_ex_140113.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContaining(String keyword);
    List<Board> findByMemberEmail(String email);
    Page<Board> findAll(Pageable pageable);
}
