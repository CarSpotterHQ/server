package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "invitation_task")
@Getter
public class InvitationTask {

    @EmbeddedId
    private InvitationTaskId id; // 복합 키

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("invitationId")
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "task_order", nullable = false)
    private Integer taskOrder; // 순서

}
