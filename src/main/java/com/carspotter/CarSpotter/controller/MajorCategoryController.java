package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.model.MajorCategory;
import com.carspotter.CarSpotter.model.dto.MajorCategoryResponseDto;
import com.carspotter.CarSpotter.response.ErrorResponse;
import com.carspotter.CarSpotter.service.MajorCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> findAllMajorCategory() {
        try {
            List<MajorCategory> allMajorCategories = majorCategoryService.getAllMajorCategories();
            return ResponseEntity.ok(allMajorCategories.stream().map(MajorCategoryResponseDto::from).collect(Collectors.toList()));
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findMajorCategoryById(@PathVariable("id") Integer id) {
        try {
            MajorCategory majorCategory = majorCategoryService.getMajorCategoryById(id);
            return ResponseEntity.ok(MajorCategoryResponseDto.from(majorCategory));
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
