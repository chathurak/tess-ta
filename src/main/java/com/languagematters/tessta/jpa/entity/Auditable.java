package com.languagematters.tessta.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "createdBy", "updatedAt", "updatedBy"},
        allowGetters = true
)
public abstract class Auditable implements Serializable {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @CreatedBy
    @Column(updatable = false)
    private Integer createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @LastModifiedBy
    private Integer updatedBy;

}