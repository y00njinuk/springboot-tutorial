package com.jinuk.tutorial.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JpaRepository<T,TD> 클래스 상속 시 기본적인 CURD 메소드가 자동으로 생성
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @implNote Repository 클래스는 항상 Entity 클래스와 같은 위치에 있어야 한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    /**
     * 기본적인 JPA 메서드로도 조회하는 것이 가능하나 아래와 같이 쿼리를 직접 매핑하여도 상관없다.
     * 또한, 규모가 있는 프로젝트에서는 FK의 조인, 복잡한 쿼리 등으로 인해 조회용 프레임워크를 따로 사용한다.
     * 대표적으로 querydsl, jooq, Mybatis 등이 있다. (필자는 querydsl을 추천하며 자세한 내용은 p.147를 참조)
     */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
