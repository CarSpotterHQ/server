package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.MajorCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorCategoryResponseDto {
    private Long id; // 대분류 ID
    private String name; // 대분류 이름

    public static MajorCategoryResponseDto from(MajorCategory majorCategory) {
        MajorCategoryResponseDto majorCategoryResponseDto = new MajorCategoryResponseDto();
        majorCategoryResponseDto.id = majorCategory.getId();
        majorCategoryResponseDto.name = majorCategory.getName();
        return majorCategoryResponseDto;
    }
}
