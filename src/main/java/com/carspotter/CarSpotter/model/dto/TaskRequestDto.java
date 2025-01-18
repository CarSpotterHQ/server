package com.carspotter.CarSpotter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    private Integer majorCategoryId;
    private Integer minorCategoryId;
    private Integer penaltyId;

    private String name;
    private String nickname;
    private String penaltyName;

}
