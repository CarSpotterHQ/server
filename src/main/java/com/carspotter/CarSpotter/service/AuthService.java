package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.dto.user.LoginDto;
import com.carspotter.CarSpotter.dto.user.UserRequestDto;
import com.carspotter.CarSpotter.dto.user.UserResponseDto;
import com.carspotter.CarSpotter.dto.user.jwt.TokenDto;
import com.carspotter.CarSpotter.dto.user.jwt.TokenRequestDto;
import com.carspotter.CarSpotter.entity.User;
import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.jwt.TokenProvider;
import com.carspotter.CarSpotter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.USER_DUPLICATION);
        }

        User user = userRequestDto.toUser(passwordEncoder);

        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public TokenDto login(LoginDto LoginDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = LoginDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.createTokenDto(authentication);

        // 4. RefreshToken 저장
        User user = userRepository.findByEmail(LoginDto.getEmail())
                .orElseThrow(() ->  new CustomException(ErrorCode.NO_LOGIN));

        user.updateRefreshToken(tokenDto.getRefreshToken());

        userRepository.save(user); // 토큰 업데이트

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto;
        User user;
        // 1. access Token이 유효한 경우
        if (tokenProvider.validateToken(tokenRequestDto.getAccessToken())) {
            // 1-1. access Token에서 Member ID 가져오기
            Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

            // 1-2. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
            user = userRepository.findById(Long.parseLong(authentication.getName()))
                    .orElseThrow(() -> new CustomException(ErrorCode.NO_LOGIN));

            // 1-3. Refresh Token 일치하는지 검사
            if (!user.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
                throw new CustomException(ErrorCode.NO_USER);
            }

            // 1-4. 새로운 access Token 생성
            tokenDto = tokenProvider.createTokenDto(authentication);
        }
        // 2. access Token이 만료된 경우
        else {
            // 2-1. Refresh Token 검증
            if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
                throw new CustomException(ErrorCode.NO_TOKEN);
            }

            // 2-2. 저장소에서 Refresh Token 값 가져옴
            user = userRepository.findByRefreshToken(tokenRequestDto.getRefreshToken())
                    .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

            // 2-3. 새로운 access Token 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), null, new ArrayList<>());
            // id로?
            tokenDto = tokenProvider.createTokenDto(authentication);
        }

        // 3. 저장소 정보 업데이트
        user.updateRefreshToken(tokenDto.getRefreshToken());
        userRepository.save(user);

        // 4. 토큰 발급
        return tokenDto;
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}