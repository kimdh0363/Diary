package com.example.Diary.global.jwt.filter;

import com.example.Diary.global.jwt.domain.RefreshToken;
import com.example.Diary.global.jwt.domain.RefreshTokenRepository;
import com.example.Diary.global.jwt.detail.CustomUserDetails;
import com.example.Diary.global.jwt.token.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenHeader(request);
        if(token!=null && jwtTokenProvider.isExpired(token)) {

            String refreshToken = jwtTokenProvider.getEmail(token);

            if(refreshToken != null && jwtTokenProvider.isExpired(refreshToken)) {

                String email = jwtTokenProvider.getEmail(refreshToken);

                Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByEmail(email);

                if(refreshTokenOptional.isEmpty()) {

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Refresh Token Invalid or Expired. Please log in again");

                    return;
                }
            }

            String email = jwtTokenProvider.getEmail(token);

            CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails,null,customUserDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            String newAccessToken = jwtTokenProvider.createAccessToken(email,authenticationToken.getAuthorities().iterator().next().getAuthority());

            response.setHeader("AccessToken",newAccessToken);

        } else if(token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Access token is missing");
        }

    }
    private String getTokenHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return null;
        }else {
            return bearerToken.substring("Bearer ".length());
        }
    }
}
