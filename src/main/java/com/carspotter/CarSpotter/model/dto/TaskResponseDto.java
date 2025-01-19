package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.InvitationTask;
import com.carspotter.CarSpotter.model.Task;
import com.carspotter.CarSpotter.model.TaskOrder;
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
    private MajorCategoryResponseDto majorCategory; // 대분류 ID
    private MinorCategoryResponseDto minorCategory; // 소분류 ID
    private PenaltyResponseDto penalty; // 벌칙 ID
    private String certificationPhoto; //인증사진

    public static TaskResponseDto from(InvitationTask invitationTask) {
        Task task = invitationTask.getTask();
        return TaskResponseDto
                .builder()
                .id(task.getId())
                .name(task.getName())
                .taskOrder(invitationTask.getTaskOrder())
                .nickname(task.getNickname())
                .certificationPhoto(task.getCertificationPhoto())
                .penalty(PenaltyResponseDto.from(task.getPenalty()))
                .majorCategory(MajorCategoryResponseDto.from(task.getMajorCategory()))
                .minorCategory(MinorCategoryResponseDto.from(task.getMinorCategory()))
                .build();
    }
}
