package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 초대장 ID

    @Column(nullable = false)
    private String title; // 초대장 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 초대장 설명

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvitationTask> invitationTasks;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // 시작 시간

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime; // 종료 시간

}
