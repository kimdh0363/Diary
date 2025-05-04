package com.example.Diary.domain.member.dto;

public record MemberSignUpRequestDto(
        String username,
        String email,
        String password
) {
}
