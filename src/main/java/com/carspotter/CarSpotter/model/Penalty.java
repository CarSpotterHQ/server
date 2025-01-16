package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tp_penalty")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 벌칙 ID

    @Column(nullable = false)
    private String name; // 벌칙 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 벌칙 상세 정보

    @Column(name = "is_secret", nullable = false)
    private Boolean isSecret = false; // 벌칙 공개 여부 (기본값: FALSE) - owner가 직접 만든건지 제공해주는 벌칙을 사용한건지

}
