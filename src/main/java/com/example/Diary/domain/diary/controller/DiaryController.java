package com.example.Diary.domain.diary.controller;

import com.example.Diary.domain.diary.dto.DiaryCreateRequestDto;
import com.example.Diary.domain.diary.dto.DiaryInfoResponse;
import com.example.Diary.domain.diary.dto.DiaryUpdateRequestDto;
import com.example.Diary.domain.diary.service.DiaryService;
import com.example.Diary.global.annotation.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

    private final DiaryService diaryService;


    @PostMapping
    public ResponseEntity<String> createDiary(@RequestBody DiaryCreateRequestDto diaryCreateRequestDto, @MemberId Long memberId) {

        diaryService.createDiary(diaryCreateRequestDto,memberId);
        return ResponseEntity.ok("일기 생성 성공");

    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<String> updateDiary(@RequestBody DiaryUpdateRequestDto updateRequestDto, @MemberId Long memberId, @PathVariable("diaryId") Long diaryId) {
        diaryService.updateDiary(updateRequestDto,memberId,diaryId);
        return ResponseEntity.ok("일기 수정 완료");
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable("diaryId") Long diaryId, @MemberId Long memberId) {
        diaryService.deleteDiary(diaryId,memberId);
        return ResponseEntity.ok("일기 삭제 완료");
    }

    @GetMapping("/me/{diaryId}")
    public ResponseEntity<DiaryInfoResponse> getDiary(@PathVariable("diaryId") Long diaryId, @MemberId Long memberId) {
        DiaryInfoResponse diaryInfoResponse = diaryService.getDiary(diaryId,memberId);
        return new ResponseEntity<>(diaryInfoResponse,HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Page<DiaryInfoResponse>> getMyAllDiaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @MemberId Long memberId
    ) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("id").descending());
        Page<DiaryInfoResponse> responsePage = diaryService.getMyAllDiaries(memberId,pageable);
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<Page<DiaryInfoResponse>> getAllPublicDiaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("id").descending());

        Page<DiaryInfoResponse> responsePage = diaryService.getAllPublicDiaries(pageable);

        return new ResponseEntity<>(responsePage,HttpStatus.OK);
    }

    @GetMapping()

}
