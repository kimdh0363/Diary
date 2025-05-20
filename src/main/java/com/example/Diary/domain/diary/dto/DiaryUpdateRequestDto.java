package com.example.Diary.domain.diary.dto;

import lombok.Builder;

@Builder
public record DiaryUpdateRequestDto(
        String content
) {
}
