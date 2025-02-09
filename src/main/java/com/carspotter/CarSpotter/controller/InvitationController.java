package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.model.Invitation;
import com.carspotter.CarSpotter.model.dto.InvitationRequestDto;
import com.carspotter.CarSpotter.model.dto.InvitationResponseDto;
import com.carspotter.CarSpotter.model.dto.TaskRequestDto;
import com.carspotter.CarSpotter.response.ErrorResponse;
import com.carspotter.CarSpotter.response.SuccessResponse;
import com.carspotter.CarSpotter.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/invitation")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @Operation(summary = "초대장 생성, 초대장 생성자의 할일 추가")
    @PostMapping
    public ResponseEntity<?> createInvitation(@RequestBody InvitationRequestDto request) {
        try {
            //Penalty, Major, Minor가 있는경우 ID기반으로 모두 존재하는 객체인지 시간 확인
            Invitation invitation = invitationService.saveInvitation(request);
            return new ResponseEntity<>(new SuccessResponse(InvitationResponseDto.from(invitation)), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "[for test] 생성된 모든 초대장 데이터 확인")
    @GetMapping()
    public ResponseEntity<Object> findAllInvitation() {
        try {
//        if (!uuid.matches("^[a-fA-F0-9\\-]{36}$")) {
//            return ResponseEntity.badRequest().body("Invalid UUID format");
//        }
            List<Invitation> invitation = invitationService.getInvitations();
            return ResponseEntity.ok(invitation.stream().map(InvitationResponseDto::from).collect(Collectors.toList()));
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "초대장 데이터 확인")
    @GetMapping("/{uuid}")
    public ResponseEntity<Object> findInvitationByUUID(@PathVariable("uuid") String uuid) {
        try {
            Invitation invitation = invitationService.findByUUID(uuid);
            if (invitation == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(InvitationResponseDto.from(invitation));
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 보상 카드 제공
    @Operation(summary = "사진 업로드, 완료 확인")
    @PutMapping(value = "/{uuid}/task/{order}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createRewardCard(@PathVariable("uuid") String uuid, @PathVariable("order") Integer order,
                                              @RequestPart(value = "certificationPhoto", required = false) MultipartFile multipartFile) {
        try {
            Invitation invitation = invitationService.saveRewardCard(uuid, order, Optional.ofNullable(multipartFile));
            return new ResponseEntity<>(new SuccessResponse(InvitationResponseDto.from(invitation)), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "task 수락 후 새로운 task 추가(두번쨰 사람, 라이벌)")
    @PostMapping(value = "/{uuid}/task/{order}")
    public ResponseEntity<?> addTask(@PathVariable("uuid") String uuid, @PathVariable("order") Integer order,
                                     @RequestBody TaskRequestDto taskRequestDto) {
        try {
            Invitation invitation = invitationService.saveInvitation(uuid, order, taskRequestDto);
            return new ResponseEntity<>(new SuccessResponse(InvitationResponseDto.from(invitation)), HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(), e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
