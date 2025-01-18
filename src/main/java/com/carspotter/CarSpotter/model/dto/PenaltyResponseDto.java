package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.Penalty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyResponseDto {
    private Long id; // 벌칙 ID
    private String name; // 벌칙 이름

    private String description; // 벌칙 상세 정보
    private Boolean isSecret = false; // 벌칙 공개 여부 (기본값: FALSE)

    public static PenaltyResponseDto from(Penalty penalty) {
        PenaltyResponseDto penaltyResponseDto = new PenaltyResponseDto();
        penaltyResponseDto.description = penalty.getDescription();
        penaltyResponseDto.id = penalty.getId();
        penaltyResponseDto.isSecret = penalty.getIsSecret();
        penaltyResponseDto.name = penalty.getName();
        return penaltyResponseDto;
    }
}
