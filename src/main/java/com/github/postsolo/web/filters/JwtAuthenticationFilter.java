package com.github.postsolo.web.filters;

import com.github.postsolo.config.security.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = jwtTokenProvider.resolveToken(request);

            if (jwtToken!=null&&!jwtTokenProvider.validToken(jwtToken)&&jwtTokenProvider.isTokenBlackListed(jwtToken)){
                Authentication auth = jwtTokenProvider.getAuth(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }catch (JwtException je){
            je.printStackTrace();
            throw new JwtException("해당 토큰은 만료되거나 유효하지 않습니다");
        }

        filterChain.doFilter(request,response);
    }
}
