package com.example.Diary.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberLogoutRequestDto(
        String email
) {
}
