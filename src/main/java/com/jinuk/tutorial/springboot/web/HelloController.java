package com.jinuk.tutorial.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 해당 컨트롤러를 JSON을 반환하는 컨트롤러로 변환
public class HelloController {

    // HTTP의 메소드 중 GET 요청만 받는 API 설계
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
