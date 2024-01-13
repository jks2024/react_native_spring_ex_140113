package com.example.react_native_spring_ex_140113.repository;


import com.example.react_native_spring_ex_140113.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findByEmailAndPwd(String email, String pwd);
}