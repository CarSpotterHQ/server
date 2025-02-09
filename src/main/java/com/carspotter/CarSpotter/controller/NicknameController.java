package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.response.ErrorResponse;
import com.carspotter.CarSpotter.service.NicknameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/nickname")
@RequiredArgsConstructor
public class NicknameController {
    private final NicknameService nicknameService;

    @GetMapping
    public ResponseEntity<Object> getRandomNickname() {
        try {
            return ResponseEntity.ok(nicknameService.getRandomNickname());
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
