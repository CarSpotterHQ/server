package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 할일 ID

    @Column(nullable = false)
    private String name; // 할일 이름

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvitationTask> invitationTasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_category_id", nullable = false)
    private MajorCategory majorCategoryId; // 대분류 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "minor_category_id", nullable = false)
    private MinorCategory minorCategoryId; // 소분류 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id", nullable = false)
    private Penalty penaltyId; // 벌칙 ID

}
