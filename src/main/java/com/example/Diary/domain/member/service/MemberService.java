package com.example.Diary.domain.member.service;

import com.example.Diary.domain.member.dto.MemberSignUpRequestDto;
import com.example.Diary.domain.member.entity.Member;
import com.example.Diary.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void signUp(MemberSignUpRequestDto signUpRequestDto) {
        if(memberRepository.existsByEmail(signUpRequestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.password());

        Member member = Member.builder()
                .username(signUpRequestDto.username())
                .email(signUpRequestDto.email())
                .password(encodedPassword)
                .build();

        memberRepository.save(member);

    }
}
