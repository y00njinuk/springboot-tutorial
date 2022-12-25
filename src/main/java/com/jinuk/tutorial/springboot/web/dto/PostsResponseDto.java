package com.jinuk.tutorial.springboot.web.dto;

import com.jinuk.tutorial.springboot.domain.posts.Posts;
import lombok.Getter;


/**
 * 요청에 대한 응답 데이터 제공으로 사용되는 DTO
 */
@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
