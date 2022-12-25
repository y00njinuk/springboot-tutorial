package com.jinuk.tutorial.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 매핑된 URL에 따라 페이지를 반환하는 컨트롤러
 * 페이지 반환 시, 앞의 경로와 뒤의 파일 확장자는 자동으로 지정
 * 앞의 경로 - src/main/resources/templates/
 * 뒤의 경로 - .mustache
 */
public class IndexController {
    /**
     * index page.
     * @return src/main/resources/templates/index.mustache
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
