package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.InvitationStatus;
import com.carspotter.CarSpotter.model.InvitationTask;
import com.carspotter.CarSpotter.model.TaskOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationResponseDto {
    private Long id;
    private String uuid;

    private String title; // 초대장 제목
    private String description; // 초대장 설명

    private List<TaskResponseDto> invitationTasks; //관련 업무들

    private LocalDateTime startTime; // 시작 시간

    private LocalDateTime endTime; // 종료 시간

    private InvitationStatus isFinished = InvitationStatus.DURING;

    public static InvitationResponseDto from(Invitation invitation) {
        InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
        invitationResponseDto.isFinished = isFinished(invitation);
        invitationResponseDto.description = invitation.getDescription();
        invitationResponseDto.endTime = invitation.getEndTime();
        invitationResponseDto.startTime = invitation.getStartTime();
        invitationResponseDto.id = invitation.getId();
        invitationResponseDto.title = invitation.getTitle();
        invitationResponseDto.uuid = invitation.getUuid();
        //TODO : task 정보 채우기
        invitationResponseDto.invitationTasks = getTaskResponseDto(invitation.getInvitationTasks());
        return invitationResponseDto;
    }

    private static InvitationStatus isFinished(Invitation invitation) {
        if (invitation.getEndTime().isBefore(LocalDateTime.now())) {
            return InvitationStatus.EXPIRED;
        }

        List<InvitationTask> invitationResponseDto = invitation.getInvitationTasks();
        if (invitationResponseDto != null && invitationResponseDto.size() == TaskOrder.values().length) {
            for (InvitationTask invitationTask : invitationResponseDto) {
                if (invitationTask.getTask().getCertificationPhoto() == null) return InvitationStatus.DURING;
            }
            return InvitationStatus.FINISHED;
        }
        return InvitationStatus.DURING;
    }

    private static List<TaskResponseDto> getTaskResponseDto(List<InvitationTask> invitationTasks) {
        ArrayList<TaskResponseDto> taskResponseDtos = new ArrayList<>();
        for (InvitationTask invitationTask : invitationTasks) {
            taskResponseDtos.add(TaskResponseDto.from(invitationTask));
        }
        return taskResponseDtos;
    }
}
