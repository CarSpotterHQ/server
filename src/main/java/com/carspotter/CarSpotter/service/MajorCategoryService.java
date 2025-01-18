package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.model.MajorCategory;
import com.carspotter.CarSpotter.repository.MajorCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MajorCategoryService {

    private final MajorCategoryRepository majorCategoryRepository;


    public List<MajorCategory> getAllMajorCategories() {
        return majorCategoryRepository.findAll();
    }

    public MajorCategory getMajorCategoryById(Long id) {
        return majorCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MajorCategory not found with id: " + id));
    }

    public MajorCategory saveMajorCategory(MajorCategory majorCategory) {
        return majorCategoryRepository.save(majorCategory);
    }

    public void deleteMajorCategory(Long id) {
        majorCategoryRepository.deleteById(id);
    }
}
