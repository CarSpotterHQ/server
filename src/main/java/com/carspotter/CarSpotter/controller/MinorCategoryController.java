package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.model.MinorCategory;
import com.carspotter.CarSpotter.model.dto.MinorCategoryResponseDto;
import com.carspotter.CarSpotter.service.MinorCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/minor-category")
@RequiredArgsConstructor
public class MinorCategoryController {
    private final MinorCategoryService minorCategoryService;

    @GetMapping("/{major-category-id}")
    public ResponseEntity<List<MinorCategoryResponseDto>> findByMajorCategoryId(@PathVariable("major-category-id") Integer id) {
        List<MinorCategory> minorCategories = minorCategoryService.getAllMinorCategoriesByMajorId(id);
        return ResponseEntity.ok(minorCategories.stream().map(MinorCategoryResponseDto::from).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MinorCategoryResponseDto> findById(@PathVariable("id") Integer id) {
        MinorCategory minorCategory = minorCategoryService.getMinorCategoryById(id);
        return ResponseEntity.ok(MinorCategoryResponseDto.from(minorCategory));
    }

}
