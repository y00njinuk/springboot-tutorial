package com.jinuk.tutorial.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.jinuk.tutorial.springboot.domain.user.Role.USER;

/**
 * 스프링 시큐리티 설정 관련 클래스
 */
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()                                                                // URL 별로 권한 관리를 설정하는 옵션의 시작점
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")  // 권한 관리 대상을 지정
                                                                                                    // URL, HTTP 메소드 별로 관리 가능
                .permitAll()                                                                        // "/"로 지정된 URL 들은 전체 열람할 수 있도록 권한 설정
                .antMatchers("/api/v1/**")
                .hasRole(USER.name())                                                               // "/api/v1/**"로 지정된 URL 들은 USER 권한을 가진 사람만 가능하도록 권한 설정
                .anyRequest()                                                                       // 설정값 이외의 나머지 URL 들에 대하여 옵션 설정
                .authenticated()                                                                    // 모두 인증된(로그인한) 사용자들에게만 허용할 수 있도록 설정
                .and()
                .logout()                                                                           // 로그아웃 기능에 대한 옵션 설정
                .logoutSuccessUrl("/")                                                              // 로그아웃 성공 시 해당 주소("/")로 이동
                .and()
                .oauth2Login()                                                                      // OAuth 2 로그인 기능에 대한 옵션 설정
                .userInfoEndpoint()                                                                 // 로그인 성공 시 가져올 사용자 정보에 대하여 설정
                .userService(customOAuth2UserService);                                              // 로그인 성공 시 후처리를 UserService 구현체로 넘김
                                                                                                    // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 처리 가능
    }
}
