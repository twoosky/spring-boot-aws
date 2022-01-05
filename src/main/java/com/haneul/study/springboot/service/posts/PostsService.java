package com.haneul.study.springboot.service.posts;

import com.haneul.study.springboot.domain.posts.PostsRepository;
import com.haneul.study.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor  // final이 선언된 모든 필드를 인자값으로 하는 생성자 자동 생성
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
