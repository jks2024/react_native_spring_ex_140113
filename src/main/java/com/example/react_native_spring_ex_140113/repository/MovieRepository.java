package com.example.react_native_spring_ex_140113.repository;

import com.example.react_native_spring_ex_140113.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
