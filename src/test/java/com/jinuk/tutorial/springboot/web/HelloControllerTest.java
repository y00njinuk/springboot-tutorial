package com.jinuk.tutorial.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 테스트 진행 시 JUnit에 내장된 실행파일 외 다른 실행파일을 실행
                             // SpringRunner? 스프링부트와 Junit 사이의 다리 역할을 한다.
@WebMvcTest(controllers = HelloController.class) // Spring MVC에 집중하는 어노테이션
                                                 // @Controller, @ControllerAdvice 사용가능
                                                 // @Service, @Component, @Repository 사용불가
public class HelloControllerTest {
    @Autowired           // 스프링이 관리하는 빈(Bean)을 주입
    private MockMvc mvc; // 웹 API 테스트 시 사용되며 GET,POST 등 API 테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))        // '/hello' 주소로 HTTP GET 요청
                .andExpect(status().isOk())          // perform 메소드 검증 - HTTP Header의 status 검사
                .andExpect(content().string(hello)); // perform 메소드 검증 - Response body 검사
    }
}
