package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.Penalty;
import com.carspotter.CarSpotter.model.dto.InvitationRequestDto;
import com.carspotter.CarSpotter.model.dto.InvitationResponseDto;
import com.carspotter.CarSpotter.model.dto.PenaltyResponseDto;
import com.carspotter.CarSpotter.service.InvitationService;
import com.carspotter.CarSpotter.service.PenaltyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/penalty")
@RequiredArgsConstructor
public class PenaltyController {
    private final PenaltyService penaltyService;

    @GetMapping("/all")
    public ResponseEntity<List<PenaltyResponseDto>> findAllPenalty() {
        List<Penalty> allPenalties = penaltyService.getAllPenalties();
        return ResponseEntity.ok(allPenalties.stream().map(PenaltyResponseDto::from).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenaltyResponseDto> findPenaltyById(@PathVariable("id") Integer id) {
        Penalty penalty = penaltyService.getPenaltyById(id);
        return ResponseEntity.ok(PenaltyResponseDto.from(penalty));
    }

}
