package com.carspotter.CarSpotter.handler;

import com.carspotter.CarSpotter.dto.user.jwt.TokenDto;
import com.carspotter.CarSpotter.dto.user.oauth.OAuthAttributes;
import com.carspotter.CarSpotter.entity.User;
import com.carspotter.CarSpotter.exception.CustomException;
import com.carspotter.CarSpotter.exception.error.ErrorCode;
import com.carspotter.CarSpotter.jwt.TokenProvider;
import com.carspotter.CarSpotter.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${social.login.redirectUrl}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuthAttributes oAuthAttributes = (OAuthAttributes) authentication.getPrincipal();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(oAuthAttributes.getEmail(),"1234");

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = jwtTokenProvider.createTokenDto(auth);

        log.info("social login user email: {} ",oAuthAttributes.getEmail());
        log.info("access token:: {} ",tokenDto.getAccessToken());
        log.info("refresh token: {}", tokenDto.getRefreshToken());

        // Retrieve user by email and ensure the user exists
        User user = userRepository.findByEmail(oAuthAttributes.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));

        Long id = user.getId();
        int newUser = 0;

        //새로 등록한 유저
        if(user.getNickname() == null) {
            user.updateRefreshToken(tokenDto.getRefreshToken());
            newUser = 1;
        }
        userRepository.save(user);

        StringBuilder sb = new StringBuilder();
        sb.append(redirectUrl).append("?refreshToken=")
                .append(tokenDto.getRefreshToken())
                .append("&&accessToken=")
                .append(tokenDto.getAccessToken())
                .append("&&id=")
                .append(id)
                .append("&&newUser=")
                .append(newUser);

        response.sendRedirect(sb.toString());

    }
}