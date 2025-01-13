package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.model.MinorCategory;
import com.carspotter.CarSpotter.repository.MinorCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MinorCategoryService {

    private final MinorCategoryRepository minorCategoryRepository;


    public List<MinorCategory> getAllMinorCategories() {
        return minorCategoryRepository.findAll();
    }

    public MinorCategory getMinorCategoryById(Integer id) {
        return minorCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MinorCategory not found with id: " + id));
    }

    public MinorCategory saveMinorCategory(MinorCategory minorCategory) {
        return minorCategoryRepository.save(minorCategory);
    }

    public void deleteMinorCategory(Integer id) {
        minorCategoryRepository.deleteById(id);
    }
}
