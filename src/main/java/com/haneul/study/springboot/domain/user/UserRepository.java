package com.haneul.study.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Optional<T>: null이 올 수 있는 T타입의 객체를 포장해주는 래퍼 클래스(Wrapper class)
    // - nullPointException을 Optional에서 제공되는 메소드로 간단히 처리 가능
    // 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드
    Optional<User> findByEmail(String email);
}
