package com.haneul.study.springboot.web;

import com.haneul.study.springboot.service.posts.PostsService;
import com.haneul.study.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor   // final이 선언된 모든 필드를 인자값으로 하는 생성자 자동 생성
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        // Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts이름의 List로 index.mustache에 전달한다.
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
    // 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 두의 파일 확장자가 자동으로 지정된다.
    // 앞의 경로: /src/main/resources/templates
    // 확장자: .mustache가 붙어 View Resolver 처리

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
