package com.carspotter.CarSpotter.dto.user;

import com.carspotter.CarSpotter.entity.Authority;
import com.carspotter.CarSpotter.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String email;

    @NotNull
    @Size(min = 2, max = 50)
    private String password;

    private String nickname; //닉네임

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

}