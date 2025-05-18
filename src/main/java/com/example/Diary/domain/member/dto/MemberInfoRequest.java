package com.example.Diary.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberInfoRequest(
        String email,
        String
) {
}
