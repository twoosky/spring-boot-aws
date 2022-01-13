package com.haneul.study.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor   // final이 선언된 모든 필드를 인자값으로 하는 생성자 자동 생성
public enum Role {

    // 스프링 시큐리티에서는 권한 코드에 항상 `ROLE_`이 앞에 있어야만 한다.
    // 그래서 코드별 키 값을 ROLE_GUEST, ROLE_USER 등으로 지정
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
