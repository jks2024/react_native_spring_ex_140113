package com.example.react_native_spring_ex_140113.repository;

import com.example.react_native_spring_ex_140113.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category>findByMemberEmail(String email);
}
