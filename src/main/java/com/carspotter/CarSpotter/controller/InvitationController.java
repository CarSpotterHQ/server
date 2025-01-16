package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.dto.InvitationRequestDto;
import com.carspotter.CarSpotter.model.dto.InvitationResponseDto;
import com.carspotter.CarSpotter.response.ErrorResponse;
import com.carspotter.CarSpotter.response.SuccessResponse;
import com.carspotter.CarSpotter.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invitation")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<?> createInvitation(@RequestBody InvitationRequestDto request) {
        try{
            //TODO : request에 대한 validation 검증
            //Penalty, Major, Minor가 있는경우 ID기반으로 모두 존재하는 객체인지 시간 확인
            Invitation invitation = invitationService.saveInvitation(request);
            return new ResponseEntity<>(new SuccessResponse(InvitationResponseDto.from(invitation)), HttpStatus.OK);
        } catch(CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(),e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> findInvitationByUUID(@PathVariable("uuid") String uuid) {
//        if (!uuid.matches("^[a-fA-F0-9\\-]{36}$")) {
//            return ResponseEntity.badRequest().body("Invalid UUID format");
//        }
        Invitation invitation = invitationService.findByUUID(uuid);
        if (invitation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(InvitationResponseDto.from(invitation));
    }

}
