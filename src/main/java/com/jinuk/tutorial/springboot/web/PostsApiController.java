package com.jinuk.tutorial.springboot.web;

import com.jinuk.tutorial.springboot.service.posts.PostsService;
import com.jinuk.tutorial.springboot.web.dto.PostsResponseDto;
import com.jinuk.tutorial.springboot.web.dto.PostsSaveRequestDto;
import com.jinuk.tutorial.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // final로 선언된 필드들에 대하여 생성자를 대신 생성해주는 어노테이션
@RestController
public class PostsApiController {
    private final PostsService postsService;
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
