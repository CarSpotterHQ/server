package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.model.Penalty;
import com.carspotter.CarSpotter.repository.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;


    public List<Penalty> getAllPenalties() {
        return penaltyRepository.findAll();
    }

    public Penalty getPenaltyById(Integer id) {
        return penaltyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Penalty not found with id: " + id));
    }

    public Penalty savePenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }

    public void deletePenalty(Integer id) {
        penaltyRepository.deleteById(id);
    }
}
