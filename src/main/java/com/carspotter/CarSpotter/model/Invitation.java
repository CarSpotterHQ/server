package com.carspotter.CarSpotter.model;

import com.carspotter.CarSpotter.model.dto.InvitationRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "tp_invitation")
public class Invitation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 초대장 ID

    @Column
    private String title; // 초대장 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 초대장 설명

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvitationTask> invitationTasks = new ArrayList<>();

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // 시작 시간

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime; // 종료 시간

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.DURING;

    public static Invitation from(InvitationRequestDto requestDto) {
        Invitation invitation = new Invitation();
        invitation.title = requestDto.getTitle();
        invitation.description = requestDto.getDescription();
        invitation.startTime = LocalDateTime.now();
        invitation.endTime = (requestDto.getEndTime() == null) ? LocalDateTime.now().plusDays(1) : requestDto.getEndTime();
        invitation.uuid = String.valueOf(UUID.randomUUID());
        return invitation;
    }

    public void addTask(InvitationTask e) { //order enum 관리?
        invitationTasks.add(e);
    }

    public void updateStatus(InvitationStatus invitationStatus) {
        this.status = invitationStatus;
    }
}
