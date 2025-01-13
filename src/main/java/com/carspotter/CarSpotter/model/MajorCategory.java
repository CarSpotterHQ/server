package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "major_category")
public class MajorCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 대분류 ID

    @Column(nullable = false)
    private String name; // 대분류 이름

    // Task와의 관계 설정
    @OneToMany(mappedBy = "majorCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MinorCategory> tasks;
}
