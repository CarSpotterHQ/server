package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.model.Penalty;
import com.carspotter.CarSpotter.model.dto.PenaltyResponseDto;
import com.carspotter.CarSpotter.response.ErrorResponse;
import com.carspotter.CarSpotter.service.PenaltyService;
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
@RequestMapping("/api/v1/penalty")
@RequiredArgsConstructor
public class PenaltyController {
    private final PenaltyService penaltyService;

    @GetMapping("/all")
    public ResponseEntity<Object> findAllPenalty() {
        try {
            List<Penalty> allPenalties = penaltyService.getAllPenalties();
            return ResponseEntity.ok(allPenalties.stream().map(PenaltyResponseDto::from).collect(Collectors.toList()));
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findPenaltyById(@PathVariable("id") Integer id) {
        try {
            Penalty penalty = penaltyService.getPenaltyById(id);
            return ResponseEntity.ok(PenaltyResponseDto.from(penalty));
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
