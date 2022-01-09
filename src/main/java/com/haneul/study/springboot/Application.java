package com.haneul.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 프로젝트의 메인 클래스
// @SpringBootApplication을 통해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성이 모두 자동으로 설정된다.
// 해당 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트 최상단에 위치해야 한다.
@SpringBootApplication
@EnableJpaAuditing    // JPA Auditing 활성화
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // SpringApplication.run으로 내장 WAS를 실행시킨다.
        // 내장 WAS를 사용하면 항상 서버에 톰캣(Tomcat)을 설치할 필요가 없고, 어떤 환경에서든지 단순 jar로 배포 가능하다.
    }
}
