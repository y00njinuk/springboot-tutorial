package com.jinuk.tutorial.springboot.web;

import com.jinuk.tutorial.springboot.service.posts.PostsService;
import com.jinuk.tutorial.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor // final로 선언된 필드들에 대하여 생성자를 대신 생성해주는 어노테이션
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }
}
