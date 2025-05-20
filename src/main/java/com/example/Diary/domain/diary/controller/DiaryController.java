package com.example.Diary.domain.diary.controller;

import com.example.Diary.domain.diary.dto.DiaryCreateRequestDto;
import com.example.Diary.domain.diary.dto.DiaryUpdateRequestDto;
import com.example.Diary.domain.diary.service.DiaryService;
import com.example.Diary.global.annotation.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

    private final DiaryService diaryService;


    @PostMapping
    public ResponseEntity<?> createDiary(@RequestBody DiaryCreateRequestDto diaryCreateRequestDto, @MemberId Long memberId) {
        diaryService.createDiary(diaryCreateRequestDto,memberId);
        return ResponseEntity.ok("일기 생성 성공");
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<?> updateDiary(@RequestBody DiaryUpdateRequestDto updateRequestDto, @MemberId Long memberId, @PathVariable("diaryId") Long diaryId) {
        diaryService.updateDiary(updateRequestDto,memberId,diaryId);
        return ResponseEntity.ok("일기 수정 완료");
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable Long diaryId, @MemberId Long memberId) {
        diaryService.deleteDiary(diaryId,memberId);
        return ResponseEntity.ok("일기 삭제 완료");
    }

}
