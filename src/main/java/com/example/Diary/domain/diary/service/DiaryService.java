package com.example.Diary.domain.diary.service;

import com.example.Diary.domain.diary.dto.DiaryCreateRequestDto;
import com.example.Diary.domain.diary.dto.DiaryUpdateRequestDto;
import com.example.Diary.domain.diary.entity.Diary;
import com.example.Diary.domain.diary.repository.DiaryRepository;
import com.example.Diary.domain.member.entity.Member;
import com.example.Diary.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void update(DiaryUpdateRequestDto updateRequestDto, Long memberId, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 일기입니다."));

        if(!Objects.equals(diary.getMember().getId(), memberId)) {
            throw new IllegalArgumentException("수정권한이 없는 회원입니다.");
        }

        diary.updateDiary(updateRequestDto.content());

    }

}
