package com.carspotter.CarSpotter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRequestDto {
    private String title; // 초대장 제목
    private String description; // 초대장 설명
    private TaskRequestDto taskRequestDto; // 할일
    private LocalDateTime endTime; // 종료 시간
}
