package com.haneul.study.springboot.config.auth;

import com.haneul.study.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity    // Spring Security 설정들을 활성화시켜 주는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // csrf().disable().headers().frameOptions().disable()
        // : h2-console 화면을 사용하기 위해 해당 옵션들을 disable한다
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                // authorizeRequests
                // : URL별 권한 관리를 설정하는 옵션의 시작점
                // - authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
                .authorizeRequests()
                // antMatchers
                // : 권한 관리 대상을 지정하는 옵션
                // - URL, HTTP 메소드별로 관리가 가능
                // permitAll() 옵션: 지정된 URL들에 전체 열람 권한을 준다.
                // hasRole() 옵션: 특정 주소에 대한 권한 부여
                // - 특정 주소("/api/v1/**")를 가진 API는 USER권한을 가진 사람만 가능하다.
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                //.antMatchers("/api/v1/**").hasRole(Role.GUEST.name()) - 이렇게 하면 GUEST권한의 사람도 API 사용 가능

                // anyRequest
                // : 설정된 값들 이외 나머지 URL들을 나타낸다.
                // authenticated() 옵션: 인증된 사용자들(로그인한 사용자들)에게만 허용
                // - 즉, 나머지 URL들은 인증된 사용자에게만 허용
                .anyRequest().authenticated()
                .and()
                .logout()     // 로그아웃 기능에 대한 여러 설정의 진입점
                .logoutSuccessUrl("/")   // 로그아웃 성공 시 "/" 주소로 이동
                .and()
                .oauth2Login()   // OAuth 2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint()   // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                // UserService
                // : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                // - 리소스 서버(소셜 서비스-구글/카카오/네이버)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 가능
                .userService(customOAuth2UserService);
    }
}
