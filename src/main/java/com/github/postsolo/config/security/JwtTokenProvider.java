package com.github.postsolo.config.security;

import com.github.postsolo.repository.users.User;
import com.github.postsolo.service.UserDetailService.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final CustomUserDetailService userDetailService;

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;
    private String key;
    @PostConstruct
    public void setUp(){
        key= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    private Long tokenValidTime = 1000L*60*60;
    public String resolveToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Token");
        return jwtToken;
    }
    public String createToken(String email, List<String> roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role",roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidTime))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public boolean validToken(String jwtToken) {

        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration().after(now);
        }catch (Exception e){
            return false;
        }
    }



    public Authentication getAuth(String jwtToken) {
        UserDetails userDetails = userDetailService.loadUserByUsername(getEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
    public String getEmail(String jwtToken){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
    //로그아웃
    private Set<String> tokenBlackList =new HashSet<>();
    public void addBlackList(String jwtToken){
        System.out.println("해당 토큰 : "+jwtToken+"은 블랙리스트에 추가되았습니다.");
        tokenBlackList.add(jwtToken);
    }

    public boolean isTokenBlackListed(String jwtToken) {
        return tokenBlackList.contains(jwtToken);
    }

}
