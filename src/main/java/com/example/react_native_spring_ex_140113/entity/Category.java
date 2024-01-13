package com.example.react_native_spring_ex_140113.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter @Setter @ToString
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 전략
    @JoinColumn(name = "member_id") // 외래키
    private Member member; // 작성자

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Board> boards;
}
