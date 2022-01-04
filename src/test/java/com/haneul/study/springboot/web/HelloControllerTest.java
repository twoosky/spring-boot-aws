package com.haneul.study.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

//
@ExtendWith(SpringExtension.class)
// @SpringBootTest + @AutoConfigureMockMvc
// - 프로젝트의 전체 컨텍스트를 로드하여 빈을 주입한다. (수많은 스프링 빈을 등록하여 테스트에 필요한 의존성 추가)
// - 속도가 느리고, 무겁기 때문에 통합테스트를 할 때 많이 사용한다.
// @WebMvcTest
// - 여러 스프링 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
// - @Controller, @ControllerAdvice 등은 사용 가능
// - @Service, @Component, @Repository 등은 사용 불가능
// - 필요한 빈만을 등록하여 테스트 진행하므로 가볍다.
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    // MockMvc: 가짜 객체를 만들어 서버에 배포하지 않고도 스프링 MVC 동작을 재현할 수 있는 클래스
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  // /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk())   // 결과 검증, Http Header status가 200인지 검증
                .andExpect(content().string(hello));  // 결과(응답 본문) 검증, Controller의 리턴값이 'hello'이므로 이 값이 맞는지
    }

    @Test
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
