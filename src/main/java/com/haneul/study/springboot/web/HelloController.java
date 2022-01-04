package com.haneul.study.springboot.web;

import com.haneul.study.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// JSON을 반환하는 컨트롤러 어노테이션
@RestController
public class HelloController {

    // @GetMapping
    // - HTTP Method Get의 요청을 받는 API
    // - @RequestMapping(method = ReqeustMethod.GET)와 같은 의미
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

}
