package com.example.Diary.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberSignUpRequestDto(
        String username,
        String email,
        String password
) {
}
