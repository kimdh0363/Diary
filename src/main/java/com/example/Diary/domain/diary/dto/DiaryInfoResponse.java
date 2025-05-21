package com.example.Diary.domain.diary.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DiaryInfoResponse(
        Long boardId,
        Long memberId,
        String title,
        String content,
        LocalDateTime createdAt
) {
}
