package com.jinuk.tutorial.springboot.config.auth.dto;

import com.jinuk.tutorial.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 인증된 사용자 정보를 저장하는 DTO 클래스
 * 인증된 사용자 정보만 필요하기에 User 클래스와 별도로 정의하여 사용
 * 세션 정보를 저장하기 위해서는 별도의 직렬화 인터페이스를 구현
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
