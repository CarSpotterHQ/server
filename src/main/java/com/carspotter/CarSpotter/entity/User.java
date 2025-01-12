package com.carspotter.CarSpotter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="refresh_token")
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
