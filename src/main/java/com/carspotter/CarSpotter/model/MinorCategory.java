package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "minor_category")
public class MinorCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 소분류 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_category_id", nullable = false)
    private MajorCategory majorCategory; // 대분류 ID와 연관

    @Column(nullable = false)
    private String name; // 소분류 이름

}
