package com.haneul.study.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스 내 모든 필드의 Getter 메소드 자동 생성
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity
public class Posts {

    @Id // 해당 테이블의 primary key(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙 설정.
    private Long id;

    // 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용.
    @Column(length = 500, nullable = false) // String 기본 사이즈 VARCHAR(255) -> 500으로 변경
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // 타입 String -> TEXT
    private String content;

    private String author;

    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}