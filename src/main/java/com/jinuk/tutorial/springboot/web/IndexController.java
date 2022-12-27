package com.jinuk.tutorial.springboot.web;

import com.jinuk.tutorial.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 매핑된 URL에 따라 페이지를 반환하는 컨트롤러
 * 페이지 반환 시, 앞의 경로와 뒤의 파일 확장자는 자동으로 지정
 * 앞의 경로 - src/main/resources/templates/
 * 뒤의 경로 - .mustache
 */
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    /**
     * index page.
     * @return src/main/resources/templates/index.mustache
     */
    @GetMapping("/")
    public String index(Model model) {
        /**
         * Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
         * postsService.findAllDEsc()로 가져온 결과를 posts 변수로 index.mustache에 전달
         */
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    /**
     * new posts page.
     * @return src/main/resources/templates/posts-save.mustache
     */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
