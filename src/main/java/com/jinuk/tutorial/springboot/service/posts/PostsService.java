package com.jinuk.tutorial.springboot.service.posts;

import com.jinuk.tutorial.springboot.domain.posts.Posts;
import com.jinuk.tutorial.springboot.domain.posts.PostsRepository;
import com.jinuk.tutorial.springboot.web.dto.PostsSaveRequestDto;
import com.jinuk.tutorial.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        
        return id;
    }
}
