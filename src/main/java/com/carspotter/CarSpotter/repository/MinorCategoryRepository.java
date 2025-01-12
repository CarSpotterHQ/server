package com.carspotter.CarSpotter.repository;

import com.carspotter.CarSpotter.model.MinorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinorCategoryRepository extends JpaRepository<MinorCategory, Integer> {
    List<MinorCategory> findByMajorCategoryId(Integer majorCategoryId);
}
