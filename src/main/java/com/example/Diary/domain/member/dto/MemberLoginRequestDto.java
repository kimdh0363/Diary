package com.example.Diary.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberLoginRequestDto(
    String email,
    String password
) {
}
