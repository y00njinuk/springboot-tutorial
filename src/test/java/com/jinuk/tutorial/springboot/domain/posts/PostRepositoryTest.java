package com.jinuk.tutorial.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // 별다른 설정없이 사용할 경우 H2 데이터베이스를 자동으로 지정
public class PostRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After // JUnit에서 테스트가 끝날 때마다 수행되는 메서드를 지정
           // 일반적으로 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기 위해 사용
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        /**
         * posts 테이블에 insert/update 쿼리 수행
         * id 값이 있다면 update 작업이 수행되고, 그렇지 않다면 insert 작업이 수행
         */
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jinuk@gmail.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
