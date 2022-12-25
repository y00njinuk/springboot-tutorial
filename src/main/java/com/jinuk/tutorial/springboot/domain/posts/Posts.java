package com.jinuk.tutorial.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스 내 모든 필드의 getter 메서드를 자동으로 생성
@NoArgsConstructor // 기본 생성자 자동 추가
                   // public Posts() { } 구문이 추가된 것과 동일
@Entity // 테이블과 매핑이 될 클래스임을 나타내는 어노테이션
        // 기본값으로 클래스의 카멜케이스 표기방식으로 테이블의 이름을 매핑
        // ex) SalesManager.java -> sales_manager table
public class Posts {
    @Id // PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙
    private Long id;

    @Column(length = 500, nullable = false) // 테이블 컬럼. 문자열의 경우 VARCHAR(255)가 기본 사이즈
                                            // 따로 지정하지 않아도 기본적으로 모든 필드들이 컬럼으로 지정됨
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String author;

    @Builder // 해당 클래스의 빌더 패턴을 생성
             // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        /** Builder Pattern
         * Posts new_post = new Posts.PostsBuilder()
         *                           .title("title")
         *                           .author("author")
         *                           .content("content")
         *                           .build();
         */
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
