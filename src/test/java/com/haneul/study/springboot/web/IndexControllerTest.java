package com.haneul.study.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
// @WebMvcTest 을 쓰면 오류나네.. 왜 안되지 얘는? JPA를 사용하는 테스트가 아닌데 왜 안되징
@SpringBootTest(webEnvironment = RANDOM_PORT)    // 랜덤 포트 실행
public class IndexControllerTest {

    @Autowired
    // TestRestTemplate을 통해 url 호출했을 때 해당 머스테치 파일에 포함된 코드 확인 가능
    private TestRestTemplate testRestTemplate;

    @Test
    public void 메인페이지_로딩() {
        //when
        String body = testRestTemplate.getForObject("/", String.class);

        //then
        // 해당 문자열이 포함되어 있는지만 비교
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
}
