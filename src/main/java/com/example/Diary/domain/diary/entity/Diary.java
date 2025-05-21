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

    private Visibility visibility;


    @Builder
    private Diary(String title, String content, Member member, Visibility visibility) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.visibility = visibility;
    }

    public void diaryUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

}
