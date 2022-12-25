package com.jinuk.tutorial.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 기능을 활성화
@SpringBootApplication // 스프링 부트의 자동 설정. 스프링 Bean 읽기와 생성을 모두 자동으로 설정
public class Application {
    public static void main(String[] args) {
        // run 호출 시 스프링 부트에 내장된 WAS를 통해 Application 클래스 실행
        SpringApplication.run(Application.class, args);
    }
}
