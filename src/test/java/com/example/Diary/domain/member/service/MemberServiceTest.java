package com.example.Diary.domain.member.service;

import com.example.Diary.domain.member.dto.MemberSignUpRequestDto;
import com.example.Diary.domain.member.entity.Member;
import com.example.Diary.domain.member.repository.MemberRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("회원의 정보를 받아와 회원가입을 진행한다.")
    @Test
    void signUp() {
        MemberSignUpRequestDto signUpRequestDto = createMember();

        memberService.signUp(signUpRequestDto);

        Member member = memberRepository.findByEmail(signUpRequestDto.email());

        assertThat(member.getEmail()).isEqualTo(signUpRequestDto.email());
        assertThat(passwordEncoder.matches(signUpRequestDto.password(), member.getPassword())).isTrue();
    }

    @DisplayName("중복된 이메일을 입력받으면 예외가 발생한다.")
    @Test
    void signUpThrowExceptionCauseByDuplicatedEmail() {

        MemberSignUpRequestDto signUpRequestDto = createMember();

        memberService.signUp(signUpRequestDto);

        MemberSignUpRequestDto duplicatedEmailSignUpRequestDto = MemberSignUpRequestDto.builder()
                .email(signUpRequestDto.email())
                .password("Test123")
                .build();

        assertThatThrownBy(() -> memberService.signUp(duplicatedEmailSignUpRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }

    static MemberSignUpRequestDto createMember() {
        return MemberSignUpRequestDto.builder()
                .email("Test@Test.com")
                .password("Test")
                .build();
    }
}