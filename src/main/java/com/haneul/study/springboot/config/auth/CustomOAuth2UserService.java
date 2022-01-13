package com.haneul.study.springboot.config.auth;

import com.haneul.study.springboot.domain.user.User;
import com.haneul.study.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
// 구글 로그인 이후 가져온 사용자의 정보(email, name, picture 등)들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원하는 클래스
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId
        // : 현재 로그인 진행 중인 서비스를 구분하는 코드
        // ex) 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // UserNameAttributeName
        // : OAuth2 로그인 진행 시 키가 되는 필드값이다. Primary Key와 같은 의미
        // - 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않는다. 구글의 기본 코드는 "sub"이다.
        // 네이버 로그인과 구글 로그인을 동시 지원할 때 사용된다.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes
        // : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        // 네이버 등 다른 소셜 로그인도 이 클래스를 사용한다.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        // SessionUser
        // : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // User 클래스를 쓰지 않고 SessionUser Dto 클래스를 새로 만들어 사용
        httpSession.setAttribute("user", new SessionUser(user));

        // DefaultOAuth2User의 권한을 가진 User를 load
        // DefaultOAuth2User 생성자
        // public DefaultOAuth2User(Collection<? extends GrantedAuthority> authorities,
        //					Map<String, Object> attributes, String nameAttributeKey) {
        return new DefaultOAuth2User(
                // singleton: Set 싱글톤 컬렉션 생성
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttibutes(),
                attributes.getNameAttributeKey());

    }

    // 사용자의 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                    .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
