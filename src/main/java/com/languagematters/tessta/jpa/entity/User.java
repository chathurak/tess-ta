package com.languagematters.tessta.jpa.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", columnDefinition = "BINARY(64) NOT NULL")
    private byte[] passwordHash;

    @Column(name = "salt", columnDefinition = "BINARY(64) NOT NULL")
    private byte[] salt;

//    @Column(name = "created_at", nullable = false)
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @Column(name = "created_by", nullable = false)
//    @CreatedBy
//    private String createdBy;
//
//    @Column(name = "updated_at", nullable = false)
//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//
//    @Column(name = "updated_by", nullable = false)
//    @LastModifiedBy
//    private String updatedby;

}
