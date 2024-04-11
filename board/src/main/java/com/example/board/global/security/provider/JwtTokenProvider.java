package com.example.board.global.security.provider;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.board.domain.auth.dto.AuthResponseDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public AuthResponseDto createToken(Authentication authentication) throws Exception {

        long EXPIRE_DURATION = 1000L * 60 * 60;
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + TimeUnit.MINUTES.toMillis(EXPIRE_DURATION));
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .claim("AUTHORITIES", authorities)
                .setSubject(authentication.getName())
                .signWith(key)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(expiration)
                .setSubject(authentication.getName())
                .signWith(key)
                .compact();

        return AuthResponseDto.builder()
                .userId(authentication.getName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseJwtClaims(accessToken);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("AUTHORITIES").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String TOKEN_HEADER = "Authorization";
        String TOKEN_PREFIX = "Bearer ";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
