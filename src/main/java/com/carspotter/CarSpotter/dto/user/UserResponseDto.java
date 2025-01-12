package com.carspotter.CarSpotter.dto.user;

import com.carspotter.CarSpotter.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String nickname;
    private String email;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .id(user.getId()).nickname(user.getNickname()).email(user.getEmail())
                .build();
    }
}
