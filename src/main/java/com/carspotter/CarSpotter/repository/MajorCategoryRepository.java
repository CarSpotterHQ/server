package com.carspotter.CarSpotter.repository;

import com.carspotter.CarSpotter.model.MajorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorCategoryRepository extends JpaRepository<MajorCategory, Integer> {
    List<MajorCategory> findByNameContaining(String name);
}
