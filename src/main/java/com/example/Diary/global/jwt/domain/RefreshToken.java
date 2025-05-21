package com.example.Diary.global.jwt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String email;

    private String token;

    public RefreshToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public void updateRefreshToken(String newToken) {
        this.token = newToken;
    }

}
