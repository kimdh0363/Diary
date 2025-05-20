package com.example.Diary.global.jwt.detail;

import com.example.Diary.domain.member.entity.Member;
import com.example.Diary.global.jwt.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getRole()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    public Long getMemberId() {
        return member.getId();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        return RefreshToken.builder()
                .email(this.getUsername())
                .token(refreshToken)
                .build();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
