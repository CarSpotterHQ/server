package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.dto.InvitationRequestDto;
import com.carspotter.CarSpotter.model.dto.InvitationResponseDto;
import com.carspotter.CarSpotter.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invitation")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationResponseDto> createInvitation(@RequestBody InvitationRequestDto request) {
        //TODO : request에 대한 validation 검증
        //Penalty, Major, Minor가 있는경우 ID기반으로 모두 존재하는 객체인지 시간 확인
        Invitation invitation = invitationService.saveInvitation(request);
        return ResponseEntity.ok(InvitationResponseDto.from(invitation));
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
