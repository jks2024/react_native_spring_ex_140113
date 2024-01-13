package com.example.react_native_spring_ex_140113.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member")
@Getter @Setter @ToString
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String pwd;
    @Column(unique = true)
    private String email;
    private String image;
    private Date regDate;

    @PrePersist // DB에 INSERT 되기 전에 실행되는 메소드
    public void prePersist() {
        regDate = new Date();
    }
}