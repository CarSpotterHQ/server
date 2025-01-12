package com.carspotter.CarSpotter.dto.user.oauth;

import com.carspotter.CarSpotter.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class OAuthAttributes implements OAuth2User, Authentication {

    private Map<String, Object> attributes;
    private String registrationId;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String registrationId) {
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    /**
     * 카카오 or 구글 소셜 로그인
     * 이외의 소셜 로그인은 분기로 나누고 해당 조건에 맞게 build 해야함
      */

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        if("kakao".equals(registrationId)) {
            return ofKakao("id",attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String,Object>)response.get("profile");

        return OAuthAttributes.builder().name((String) profile.get("nickname"))
                .email((String) response.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder().name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(name)
                .build();
    }


    @Override
    public Map<String, Object> getAttribute(String name) {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }
}