package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "penalty")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 벌칙 ID

    @Column(nullable = false)
    private String name; // 벌칙 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 벌칙 상세 정보

    @Column(name = "is_secret", nullable = false)
    private Boolean isSecret = false; // 벌칙 공개 여부 (기본값: FALSE)

}