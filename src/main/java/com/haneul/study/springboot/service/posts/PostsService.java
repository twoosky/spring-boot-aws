package com.haneul.study.springboot.service.posts;

import com.haneul.study.springboot.domain.posts.Posts;
import com.haneul.study.springboot.domain.posts.PostsRepository;
import com.haneul.study.springboot.web.dto.PostsListResponseDto;
import com.haneul.study.springboot.web.dto.PostsResponseDto;
import com.haneul.study.springboot.web.dto.PostsSaveRequestDto;
import com.haneul.study.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor  // final이 선언된 모든 필드를 인자값으로 하는 생성자 자동 생성
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // update 쿼리 안날려도 되나?
    // : JPA의 영속성 컨텍스트로 인해 쿼리 필요 없음.
    // 영속성 컨텍스트: 엔티티를 영구 저장하는 환경
    // JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 데이터베이스로부터 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태
    // 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경내용 반영
    // 즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다 -> '더티체킹'
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id ="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        return new PostsResponseDto(posts);
    }

    // readOnly = true: 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선된다.
    // - 등록, 수정, 삭제 기능이 전형 ㅓㅄ는 서비스 메소드에서 사용 추천
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                // .map(posts -> new PostsListResponseDto(posts))와 같은 의미
                // : postsRepository 결과로 넘어온 Posts stream을 map을 통해 PostsListResponseDto 변환 -> List로 변환하는 메소드
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        // 엔티티를 파라미터로 받아 삭제할 수도 있고, deleteById 메소드를 이용하면 id로 삭제할 수도 있다.
        // 존재하는 Posts인지 확인을 위해 findById()로 조회 후 삭제
        postsRepository.delete(posts);
    }
}
