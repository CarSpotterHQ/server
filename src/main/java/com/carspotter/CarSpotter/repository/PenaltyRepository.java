package com.carspotter.CarSpotter.repository;

import com.carspotter.CarSpotter.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    List<Penalty> findByIsSecretTrue();
}
