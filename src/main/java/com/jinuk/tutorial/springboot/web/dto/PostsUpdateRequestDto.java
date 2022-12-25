package com.jinuk.tutorial.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 업데이트 작업과 관련된 DTO
 *
 * @apiNote 실제 업데이트는 DAO에서 진행되며 DTO는 이에 필요한 데이터 교환의 역할을 한다.
 */
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
