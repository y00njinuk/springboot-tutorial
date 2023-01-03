package com.jinuk.tutorial.springboot.web;

import com.jinuk.tutorial.springboot.domain.posts.Posts;
import com.jinuk.tutorial.springboot.domain.posts.PostsRepository;
import com.jinuk.tutorial.springboot.web.dto.PostsSaveRequestDto;
import com.jinuk.tutorial.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // spring-security-test 에서 제공하는 어노테이션
                                  // 인증이 보장된 가짜 사용자를 생성
                                  // ROLE_USER 권한을 가진 사용자가 API를 호출하는 것과 동일함
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정된다() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void Posts_정상적인_ID값으로_요청시_응답을반환한다() {
        // given
        String title = "title";
        String content = "content";
        String author = "author";

        Posts newPosts = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        postsRepository.save(newPosts);
        Long getId = newPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + getId;
        ResponseEntity<Posts> responseEntity = restTemplate.getForEntity(url, Posts.class);

        assertThat(responseEntity.getBody().getTitle()).isEqualTo(title);
        assertThat(responseEntity.getBody().getContent()).isEqualTo(content);
        assertThat(responseEntity.getBody().getAuthor()).isEqualTo(author);
    }

    @Test
    public void Posts_유효하지않은_ID값으로_요청시_에러를반환한다() {
        // given
        String title = "title";
        String content = "content";
        String author = "author";

        Posts newPosts = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        postsRepository.save(newPosts);
        Long getId = newPosts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + getId + "dummy";
        ResponseEntity<Posts> responseEntity = restTemplate.getForEntity(url, Posts.class);

        System.out.println(responseEntity.getStatusCode()); // 400 BAD_REQUEST

        // status code is in the HTTP series HttpStatus.Series.CLIENT_ERROR or HttpStatus.Series.SERVER_ERROR
        assertThat(responseEntity.getStatusCode().isError());
    }
}
