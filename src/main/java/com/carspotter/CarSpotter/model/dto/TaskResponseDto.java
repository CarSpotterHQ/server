package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.MajorCategory;
import com.carspotter.CarSpotter.model.MinorCategory;
import com.carspotter.CarSpotter.model.Penalty;
import com.carspotter.CarSpotter.model.TaskOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {
    private Long id; // 할일 ID
    private TaskOrder taskOrder;
    private String name; // 할일 이름

    private String nickname; //닉네임
    private MajorCategory majorCategory; // 대분류 ID
    private MinorCategory minorCategory; // 소분류 ID
    private Penalty penalty; // 벌칙 ID

}
