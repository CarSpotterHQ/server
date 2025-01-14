package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tp_invitation_task")
@Getter
@NoArgsConstructor
public class InvitationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 단일키로 변경

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "task_order", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TaskOrder taskOrder; // 순서

    public InvitationTask(Invitation invitation, Task task, TaskOrder taskOrder) {
        this.invitation = invitation;
        this.task = task;
        this.taskOrder = taskOrder;
    }
}
