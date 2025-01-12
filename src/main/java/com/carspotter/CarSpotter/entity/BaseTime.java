package com.carspotter.CarSpotter.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    @CreatedDate // entity가 생성되어 저장될 때 시간
    private LocalDateTime createdDate;

    @LastModifiedDate // entity 값을 변경할 때 시간
    private LocalDateTime modifiedDate;

}