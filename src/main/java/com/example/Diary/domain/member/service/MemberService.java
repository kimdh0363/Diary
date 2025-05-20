package com.example.Diary.domain.member.service;

import com.example.Diary.domain.member.dto.MemberInfoResponse;
import com.example.Diary.domain.member.dto.MemberLogoutRequestDto;
import com.example.Diary.domain.member.dto.MemberSignUpRequestDto;
import com.example.Diary.domain.member.entity.Member;
import com.example.Diary.domain.member.repository.MemberRepository;
import com.example.Diary.global.jwt.domain.RefreshToken;
import com.example.Diary.global.jwt.domain.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    public void signUp(MemberSignUpRequestDto signUpRequestDto) {
        if(memberRepository.existsByEmail(signUpRequestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.password());

        Member member = Member.builder()
                .email(signUpRequestDto.email())
                .password(encodedPassword)
                .build();

        memberRepository.save(member);

    }

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        MemberInfoResponse memberInfoResponse = MemberInfoResponse.builder()
                .email(member.getEmail())
                .createAt(member.getCreatedAt())
                .build();

        return memberInfoResponse;
    }

    public void logout(Long memberId) {

        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {

            Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByEmail(memberOptional.get().getEmail());

            if(refreshTokenOptional.isPresent()) {

                RefreshToken refreshToken = RefreshToken.builder()
                        .email(refreshTokenOptional.get().getEmail())
                        .token(refreshTokenOptional.get().getToken())
                        .build();

                refreshTokenRepository.delete(refreshToken);

            }

        } else {

            throw new IllegalArgumentException("존재하지 않는 회원입니다.");

        }
    }


}
