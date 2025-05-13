package com.example.Diary.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
public record JsonWebTokenDto(
        String accessToken,
        String refreshToken

) {
}
