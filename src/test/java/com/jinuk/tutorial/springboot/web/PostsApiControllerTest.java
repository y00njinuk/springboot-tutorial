package com.jinuk.tutorial.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinuk.tutorial.springboot.domain.posts.Posts;
import com.jinuk.tutorial.springboot.domain.posts.PostsRepository;
import com.jinuk.tutorial.springboot.web.dto.PostsSaveRequestDto;
import com.jinuk.tutorial.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // spring-security-test ?????? ???????????? ???????????????
                                  // ????????? ????????? ?????? ???????????? ??????
                                  // ROLE_USER ????????? ?????? ???????????? API??? ???????????? ?????? ?????????
    public void Posts_????????????() throws Exception {
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
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_????????????() throws Exception {
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

        // when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_????????????_ID?????????_?????????_?????????????????????() throws Exception {
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

        // when
        MvcResult res = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        // then
        Posts expected = new ObjectMapper().readValue(
                res.getResponse().getContentAsByteArray(), Posts.class);

        assertThat(expected.getTitle()).isEqualTo(title);
        assertThat(expected.getContent()).isEqualTo(content);
        assertThat(expected.getAuthor()).isEqualTo(author);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_??????????????????_ID?????????_?????????_?????????????????????() throws Exception {
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

        // when, then
        mvc.perform(get(url)).andExpect(status().is4xxClientError());
    }
}
