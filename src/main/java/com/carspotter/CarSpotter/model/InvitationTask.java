package com.carspotter.CarSpotter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tp_invitation_task")
@Getter
@NoArgsConstructor
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
    @Enumerated(EnumType.ORDINAL)
    private TaskOrder taskOrder; // 순서

    public InvitationTask(Invitation invitation, Task task, TaskOrder taskOrder) {
        this.id = new InvitationTaskId(invitation.getId(), task.getId());
        this.invitation = invitation;
        this.task = task;
        this.taskOrder = taskOrder;
    }
}
