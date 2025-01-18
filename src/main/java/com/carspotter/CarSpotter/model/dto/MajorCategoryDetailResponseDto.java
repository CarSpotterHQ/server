package com.carspotter.CarSpotter.model.dto;

import com.carspotter.CarSpotter.model.MajorCategory;
import com.carspotter.CarSpotter.model.MinorCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorCategoryDetailResponseDto {
    private MajorCategoryResponseDto major;

    private List<MinorCategoryResponseDto> minorCategories;

    public static MajorCategoryDetailResponseDto from(MajorCategory majorCategory, List<MinorCategory> minorCategoryResponseDtoList) {
        MajorCategoryDetailResponseDto majorCategoryResponseDto = new MajorCategoryDetailResponseDto();
        majorCategoryResponseDto.major = MajorCategoryResponseDto.from(majorCategory);
        majorCategoryResponseDto.minorCategories = minorCategoryResponseDtoList.stream().map(MinorCategoryResponseDto::from).collect(Collectors.toList());
        return majorCategoryResponseDto;
    }
}
