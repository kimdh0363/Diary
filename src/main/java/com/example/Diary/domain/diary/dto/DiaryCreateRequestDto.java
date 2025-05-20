package com.example.Diary.domain.diary.dto;

import com.example.Diary.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record DiaryCreateRequestDto(
        String title,
        String content
) {
}
