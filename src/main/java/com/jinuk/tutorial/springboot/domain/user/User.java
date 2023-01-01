package com.jinuk.tutorial.springboot.domain.user;

import com.jinuk.tutorial.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 사용자 정보를 담당할 도메인 클래스
 */
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    /*
        @Enumerated(EnumType.String)?
        - JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지 결정
        - 기본적으로는 int로 된 숫자가 저장된다.
        - 여기선 숫자가 저장되면 무슨 의미인지 알 수 없기에 문자열 타입으로 저장한다.
    */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
