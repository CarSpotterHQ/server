package com.carspotter.CarSpotter.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class InvitationTaskId implements Serializable {

    private Long invitationId;
    private Long taskId;


}
