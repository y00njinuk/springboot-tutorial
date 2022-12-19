package com.jinuk.tutorial.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository<T,TD> 클래스 상속 시 기본적인 CURD 메소드가 자동으로 생성
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @implNote Repository 클래스는 항상 Entity 클래스와 같은 위치에 있어야 한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // nothing
}
