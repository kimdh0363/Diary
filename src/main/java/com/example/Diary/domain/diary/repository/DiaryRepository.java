package com.example.Diary.domain.diary.repository;

import com.example.Diary.domain.diary.dto.DiaryInfoResponse;
import com.example.Diary.domain.diary.entity.Diary;
import com.example.Diary.domain.diary.entity.Visibility;
import com.example.Diary.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary,Long> {
    Page<Diary> findAllByMember(Member member, Pageable pageable);

    Page<Diary> findAllByVisibility(Visibility visibility, Pageable pageable);
}
