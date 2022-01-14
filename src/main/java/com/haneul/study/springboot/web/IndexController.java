package com.haneul.study.springboot.web;

import com.haneul.study.springboot.config.auth.LoginUser;
import com.haneul.study.springboot.config.auth.dto.SessionUser;
import com.haneul.study.springboot.service.posts.PostsService;
import com.haneul.study.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor   // final이 선언된 모든 필드를 인자값으로 하는 생성자 자동 생성
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        // Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts이름의 List로 index.mustache에 전달한다.
        model.addAttribute("posts", postsService.findAllDesc());

        // 31번 라인 코드와 @LoginUser 어노테이션과 같은 역할
        // : 어느 컨트롤러든지 @LoginUser 어노테이션을 사용하면 세션 정보를 가져올 수 있다.
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // - CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성했다.
        // - 즉, 로그인 성공 시 httpSerssion.getAttribute("user")에서 값을 가져올 수 있다.
        // - CustomOAuth2UserService에서 httpSession.setAttribute("user", new SessionUser(user))로 값을 넣었으므로 "user"를 인자로 주어 값 가져옴

        // 세션에 저장된 값이 있을 때만 model에 userName으로 등록
        // 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다. (index.mustache 설정에 따라)
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
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
