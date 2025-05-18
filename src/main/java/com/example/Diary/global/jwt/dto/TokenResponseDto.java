package com.example.Diary.global.jwt.dto;

import lombok.Builder;

@Builder
public record TokenResponseDto(
        String grantType,
        String accessToken,
        String refreshToken,
        long accessTokenExpiresIn
) {
}
