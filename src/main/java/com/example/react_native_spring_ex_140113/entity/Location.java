package com.example.react_native_spring_ex_140113.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationId;

    @OneToOne(fetch = FetchType.LAZY) // 지연 전략
    @JoinColumn(name = "board_id")
    private Board board;

    private String address;
    private Double latitude;
    private Double longitude;
}