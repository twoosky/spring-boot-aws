package com.haneul.study.springboot.domain.posts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long>{

    // SpringDataJpa에서 제공하지 않는 메소드는 @Query 어노테이션 사용해 쿼리 작성
    // querydsl 사용 추천 - 타입 안정성이 보장되므로
    // : 단순한 문자열로 쿼리를 생성하는 것이 아니라, 메소드를 기반으로 쿼리 생성하므로 토아나 존재하지 않는 컬럼명을 명시할 경우 IDE에서 자동 검출
    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();
}
