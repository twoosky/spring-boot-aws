package com.haneul.study.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Target(ElementType.   )
// : 커스텀 어노테이션이 생성될 수 있는 위치
// ElementType.PACKAGE : 패키지 선언
// ElementType.TYPE : 타입 선언시 어노테이션 적용 가능
// ElementType.ANNOTATION_TYPE : 어노테이션 타입 선언
// ElementType.CONSTRUCTOR : 생성자 선언
// ElementType.FIELD : 멤버 변수 선언 시 어노테이션 적용 가능
// ElementType.LOCAL_VARIABLE : 지역 변수 선언
// ElementType.METHOD : 메서드 선언
// ElementType.PARAMETER : 메소드의 파라미터로 선언된 객체에 어노테이션 적용 가능
// ElementType.TYPE_PARAMETER : 전달인자 타입 선언
// ElementType.TYPE_USE : 타입 선언

@Target(ElementType.PARAMETER)
// @Retention
// : 어노테이션이 실제로 적용되고 유지되는 범위
// - 컴파일 이후에도 JVM에서 참고가 가능한 RUNTIME으로 지정함
// RetentionPolicy.RUNTIME: 컴파일 이후에도 JVM에 의해 계속 참조 가능 주로 리플렉션이나 로깅에 많이 사용
// RetentionPolicy.CLASS: 컴파일러가 클래스를 참조할 때까지 유효
// RetentionPolicySOURCE: 컴파일 전까지만 유효, 즉 컴파일 이후에는 사라진다.
@Retention(RetentionPolicy.RUNTIME)
// @interface: 해당 클래스 이름을 가진 어노테이션 생성
// 메소드 인자로 세션값을 바로 받아올 수 있는 커스텀 어노테이션을 생성한다
public @interface LoginUser {
}
