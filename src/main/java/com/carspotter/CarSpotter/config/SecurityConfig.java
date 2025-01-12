package com.carspotter.CarSpotter.config;

import com.carspotter.CarSpotter.exception.JwtAccessDeniedHandler;
import com.carspotter.CarSpotter.exception.JwtAuthenticationEntryPoint;
import com.carspotter.CarSpotter.handler.Oauth2SuccessHandler;
import com.carspotter.CarSpotter.jwt.JwtFilter;
import com.carspotter.CarSpotter.jwt.TokenProvider;
import com.carspotter.CarSpotter.repository.UserRepository;
import com.carspotter.CarSpotter.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // 유효한 자격 증명을 제공하지 않고 접근하려고 할때 401
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler; // 필요한 권한이 존재하지 않은 경우 403 에러
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomOauth2UserService customOauth2UserService = new CustomOauth2UserService(userRepository,passwordEncoder());

        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 예외 처리
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                // 프레임 옵션 설정 (H2-console 등 사용 시 필요)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )

                // 세션 정책: STATELESS
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // HTTP 요청 인증/인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/auth/**",
                                "/api/v1/test/**",
                                "/oauth2/**"
                        ).permitAll() // 공개 경로
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )

                // CORS 설정 적용
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oauth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOauth2UserService)
                        )
                );

        // JWT 필터 추가
        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);       // 서버의 json 응답을 JS로 처리가능하게 함
        config.addAllowedOriginPattern("*");    // springboot cors 설정 시, allowCredentials(true)와 allowedOrigin("*") 같이 사용 불가하게 업뎃
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
