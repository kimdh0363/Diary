package com.example.Diary.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String email;

    private String role;

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = "ROLE_USER";
    }

    @Builder
    public Member(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
