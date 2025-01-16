package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.model.MajorCategory;
import com.carspotter.CarSpotter.model.dto.MajorCategoryResponseDto;
import com.carspotter.CarSpotter.service.MajorCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/major-category")
@RequiredArgsConstructor
public class MajorCategoryController {
    private final MajorCategoryService majorCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<MajorCategoryResponseDto>> findAllMajorCategory() {
        List<MajorCategory> allMajorCategories = majorCategoryService.getAllMajorCategories();
        return ResponseEntity.ok(allMajorCategories.stream().map(MajorCategoryResponseDto::from).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorCategoryResponseDto> findMajorCategoryById(@PathVariable("id") Integer id) {
        MajorCategory majorCategory = majorCategoryService.getMajorCategoryById(id);
        return ResponseEntity.ok(MajorCategoryResponseDto.from(majorCategory));
    }

}
