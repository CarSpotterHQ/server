package com.carspotter.CarSpotter.service;

import com.carspotter.CarSpotter.dto.user.oauth.OAuthAttributes;
import com.carspotter.CarSpotter.entity.Authority;
import com.carspotter.CarSpotter.entity.User;
import com.carspotter.CarSpotter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomOauth2UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        save(attributes);

        return attributes;
    }

    private void save(OAuthAttributes attributes) {

        String email = attributes.getEmail();
        Optional<User> user = userRepository.findByEmail(email);
        //기존에 저장된 것이 없었다면
        if(!user.isPresent()) {
            userRepository.save(
                    User
                            .builder()
                            .email(attributes.getEmail())
                            .nickname(attributes.getName())
                            .password(passwordEncoder.encode("1234"))
                            .authority(Authority.ROLE_USER)
                            .build());
        }
    }


}