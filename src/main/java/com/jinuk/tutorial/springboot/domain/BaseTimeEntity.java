package com.jinuk.tutorial.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


/**
 * Entity들의 상위 클래스로서 createdDate, modifiedDate를 자동으로 관리
 */
@Getter
@MappedSuperclass // JPA Entity 클래스들이 해당 클래스를 상속할 경우,
                  // 아래의 정의된 필드들도 칼럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 포함
public abstract class BaseTimeEntity {
    @CreatedDate // Entity가 생성되어 저장될 때 시간이 자동으로 저장
    private LocalDateTime createDate;

    @LastModifiedDate // 조회환 Entity의 값을 변경할 때 시간이 자동으로 저장
    private LocalDateTime modifiedDate;
}
