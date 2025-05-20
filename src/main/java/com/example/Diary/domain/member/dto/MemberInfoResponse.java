package com.example.Diary.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberInfoResponse(
        String email,
        LocalDateTime createAt
) {
}
