package com.example.Diary.global.jwt.filter;

import com.example.Diary.domain.member.dto.MemberLoginRequestDto;
import com.example.Diary.global.jwt.domain.RefreshToken;
import com.example.Diary.global.jwt.domain.RefreshTokenRepository;
import com.example.Diary.global.jwt.detail.CustomUserDetails;
import com.example.Diary.global.jwt.dto.TokenResponseDto;
import com.example.Diary.global.jwt.token.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MemberLoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), MemberLoginRequestDto.class);

            System.out.println("로그인 시도 - 이메일: " + loginRequest.email());

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 JSON 파싱 실패", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();


        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByEmail(email);

        String accessToken = jwtTokenProvider.createAccessToken(email, userDetails.getAuthorities().iterator().next().getAuthority());
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        if(refreshTokenOptional.isEmpty()) {
            refreshTokenRepository.save(new RefreshToken(email, refreshToken));
        }else {
            refreshToken = refreshTokenOptional.get().getToken();
        }

        TokenResponseDto tokenDto = TokenResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenTime())
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), tokenDto);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("로그인 실패: " + failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


    }
}
