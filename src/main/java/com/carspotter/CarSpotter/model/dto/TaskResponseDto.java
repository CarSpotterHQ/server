package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDto {
    private Long id; // 할일 ID
    private TaskOrder taskOrder;
    private String name; // 할일 이름

    private String nickname; //닉네임
    private MajorCategory majorCategory; // 대분류 ID
    private MinorCategory minorCategory; // 소분류 ID
    private Penalty penalty; // 벌칙 ID

    public static TaskResponseDto from(InvitationTask invitationTask) {
        Task task = invitationTask.getTask();
        return new TaskResponseDtoBuilder()
                .id(task.getId())
                .name(task.getName())
                .taskOrder(invitationTask.getTaskOrder())
                .nickname(task.getNickname())
                .build();

    }
}
