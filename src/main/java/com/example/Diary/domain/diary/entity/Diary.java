package com.example.Diary.domain.diary.entity;

import com.example.Diary.domain.member.entity.Member;
import com.example.Diary.global.template.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    private Diary(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void updateDiary(String content) {
        this.content = content;
    }

}
