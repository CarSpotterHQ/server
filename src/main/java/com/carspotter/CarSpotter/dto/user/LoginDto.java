package com.carspotter.CarSpotter.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
//    @Size(min = 10, max = 100)
    private String email;

    @NotNull
    @Size(min = 2, max = 50)
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}