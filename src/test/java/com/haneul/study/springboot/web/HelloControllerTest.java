package com.haneul.study.springboot.web;

import com.haneul.study.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

// @SpringBootTest + @AutoConfigureMockMvc
// - 프로젝트의 전체 컨텍스트를 로드하여 빈을 주입한다. (수많은 스프링 빈을 등록하여 테스트에 필요한 의존성 추가)
// - 속도가 느리고, 무겁기 때문에 통합테스트를 할 때 많이 사용한다.
// @WebMvcTest
// - 여러 스프링 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
// - @Controller, @ControllerAdvice 등은 사용 가능
// - @Service, @Component, @Repository 등은 사용 불가능
// - 필요한 빈만을 등록하여 테스트 진행하므로 가볍다.
// @WebMvcTest에서의 security Test (p220)
// : @WebMvcTest는 WebSecurityConfigurerAdapter, WebMvcConfigurer를 비롯한 @ControllerAdvice, @Controller를 읽는다.
// - 즉, @Repository, @Service, @Component는 스캔 대상이 아니다.
// - 그러므로 SecurityConfig는 읽었지만, CustomOAuth2UserService는 읽을 수 없어 테스트 실행 시 에러가 발생한다.
// - 아래와 같이 스캔 대상에서 SecurityConfig 제거해 해결
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
@ExtendWith(SpringExtension.class)
public class HelloControllerTest {

    @Autowired
    // MockMvc: 가짜 객체를 만들어 서버에 배포하지 않고도 스프링 MVC 동작을 재현할 수 있는 클래스
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  // /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk())   // 결과 검증, Http Header status가 200인지 검증
                .andExpect(content().string(hello));  // 결과(응답 본문) 검증, Controller의 리턴값이 'hello'이므로 이 값이 맞는지
    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                                // param: API테스트할 때 사용될 요청 파라미터 설정
                                // - 값은 String만 허용되므로 숫자/날짜 등의 데이터를 등록할 때는 반드시 문자열로 변경해야 한다.
                                .param("name", name)
                                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // jsonPath: JSON 응답값을 필드별로 검증할 수 있는 메소드
                // - $를 기준으로 필드명 명시
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}

// p221
// test 문제
// 1. @WebMvcTest는 WebSecurityConfigurerAdapter, WebMvcConfigurer 파일은 읽지만, @Service는 스캔 대상이 아니므로 CustomOAuth2UserService를 읽을 수 없어 오류
// 해결책: SecurityConfig 파일을 스캔 대상에서 제외한다.
// 2. @WebMvcTest에 의해 @SpringBootApplication 어노테이션이 붙은 파일을 스캔한다.
// - 이때, @EnableJpaAuditing 어노테이션이 함께 있으므로 얘도 스캔함.
// - @EnableJpaAuditing 를 사용하기 위해선 최소 하나의 @Entity 클래스가 필요하다. @WebMvcTest에서는 없으므로 테스트 오류
// 해결책: @EnableJpaAuditing과 @SpringBootApplication을 분리한다.
// - @WebMvcTest는 일반적인 @Configuration은 스캔하지 않는다.
