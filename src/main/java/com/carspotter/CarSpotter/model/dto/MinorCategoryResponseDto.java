package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.MajorCategory;
import com.carspotter.CarSpotter.model.MinorCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinorCategoryResponseDto {
    private Long id; // 소분류 ID
    private String name; // 소분류 이름

    public static MinorCategoryResponseDto from(MinorCategory minorCategory) {
        MinorCategoryResponseDto minorCategoryResponseDto = new MinorCategoryResponseDto();
        minorCategoryResponseDto.id = minorCategory.getId();
        minorCategoryResponseDto.name = minorCategory.getName();
        return minorCategoryResponseDto;
    }
}