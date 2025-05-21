package com.example.Diary.domain.diary.service;

import com.example.Diary.domain.diary.dto.DiaryCreateRequestDto;
import com.example.Diary.domain.diary.dto.DiaryInfoResponse;
import com.example.Diary.domain.diary.dto.DiaryUpdateRequestDto;
import com.example.Diary.domain.diary.entity.Diary;
import com.example.Diary.domain.diary.repository.DiaryRepository;
import com.example.Diary.domain.member.entity.Member;
import com.example.Diary.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    public void createDiary(DiaryCreateRequestDto diaryCreateRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Diary diary = Diary.builder()
                .title(diaryCreateRequestDto.title())
                .content(diaryCreateRequestDto.content())
                .member(member)
                .build();

        diaryRepository.save(diary);
    }

    @Transactional
    public void updateDiary(DiaryUpdateRequestDto updateRequestDto, Long memberId, Long diaryId) {
        Diary diary = findDiary(diaryId);

        validateMemberId(diary.getMember().getId(), memberId);

        diary.diaryUpdate(updateRequestDto.title(), updateRequestDto.content());

    }

    @Transactional
    public void deleteDiary(Long diaryId, Long memberId) {
        Diary diary = findDiary(diaryId);

        validateMemberId(diary.getMember().getId(),memberId);

        diaryRepository.delete(diary);


    }

    @Transactional(readOnly = true)
    public DiaryInfoResponse getDiary (Long diaryId, Long memberId) {
        Diary diary = findDiary(diaryId);

        return DiaryInfoResponse.builder()
                .boardId(diaryId)
                .memberId(memberId)
                .title(diary.getTitle())
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .build();


    }

    @Transactional(readOnly = true)
    public Page<DiaryInfoResponse> getMyAllDiaries(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));

        Page<Diary> diaries= diaryRepository.findAllByMember(member, pageable);

        return diaries.map(diary -> DiaryInfoResponse.builder()
                        .boardId(diary.getId())
                        .memberId(diary.getMember().getId())
                        .title(diary.getTitle())
                        .content(diary.getContent())
                        .createdAt(diary.getCreatedAt())
                        .build());

    }


    public Diary findDiary(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 일기입니다."));

    }

    public void validateMemberId(Long diaryMemberId, Long memberId) {
        if(!diaryMemberId.equals(memberId)) {
            throw new IllegalArgumentException("해당 일기에 대한 권한이 없는 회원입니다.");
        }
    }
}
