package com.carspotter.CarSpotter.controller;

import com.carspotter.CarSpotter.dto.user.LoginDto;
import com.carspotter.CarSpotter.dto.user.UserRequestDto;
import com.carspotter.CarSpotter.dto.user.UserResponseDto;
import com.carspotter.CarSpotter.dto.user.jwt.TokenDto;
import com.carspotter.CarSpotter.dto.user.jwt.TokenRequestDto;
import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.response.ErrorResponse;
import com.carspotter.CarSpotter.response.SuccessResponse;
import com.carspotter.CarSpotter.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody UserRequestDto userRequestDto) {
        try{
            UserResponseDto userResponseDto = authService.signup(userRequestDto);
            return new ResponseEntity<>(new SuccessResponse(userResponseDto), HttpStatus.OK);
        } catch(CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(),e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try{
            TokenDto tokenDto = authService.login(loginDto);
            return new ResponseEntity<>(new SuccessResponse(tokenDto), HttpStatus.OK);
        } catch(CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(),e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/access") // 토큰 재발급
    public ResponseEntity<?> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        try{
            TokenDto tokenDto = authService.reissue(tokenRequestDto);
            return new ResponseEntity<>(new SuccessResponse(tokenDto), HttpStatus.OK);
        } catch(CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(),e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-email/{email}") // 유저 이메일 중복 검사
    public ResponseEntity<?> checkEmail(@PathVariable String email){
        try{
            boolean checkEmail = authService.checkEmail(email);
            return new ResponseEntity<>(new SuccessResponse(checkEmail), HttpStatus.OK);
        } catch(CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode().getHttpStatus(),e.getMessage()), e.getErrorCode().getHttpStatus());
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
