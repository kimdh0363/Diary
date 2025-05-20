package com.example.Diary.domain.member.controller;

import com.example.Diary.domain.member.dto.MemberInfoResponse;
import com.example.Diary.domain.member.dto.MemberLogoutRequestDto;
import com.example.Diary.domain.member.dto.MemberSignUpRequestDto;
import com.example.Diary.domain.member.service.MemberService;
import com.example.Diary.global.annotation.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/join")
    public ResponseEntity<?> signUp(@RequestBody MemberSignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@MemberId Long memberId) {
        memberService.logout(memberId);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> getMyMemberInfo(@MemberId Long memberId) {
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(memberId);
        return new ResponseEntity<>(memberInfoResponse, HttpStatus.OK);
    }
}
