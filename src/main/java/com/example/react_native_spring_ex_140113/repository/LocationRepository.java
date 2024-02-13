package com.example.react_native_spring_ex_140113.repository;

import com.example.react_native_spring_ex_140113.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
