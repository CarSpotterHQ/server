package com.carspotter.CarSpotter.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InvitationTaskId implements Serializable {

    private Integer invitationId;
    private Integer taskId;

}
