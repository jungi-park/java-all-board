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
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	public AuthResponseDto createToken(Authentication authentication) throws Exception {

		long EXPIRE_DURATION = 1000L * 60 * 60;
		Date issuedAt = new Date();
		Date expiration = new Date(issuedAt.getTime() + TimeUnit.MINUTES.toMillis(EXPIRE_DURATION));
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		String accessToken = Jwts.builder().setIssuedAt(issuedAt).setExpiration(expiration)
				.claim("AUTHORITIES", authorities).setSubject(authentication.getName()).signWith(key).compact();

		String refreshToken = Jwts.builder().setExpiration(expiration).setSubject(authentication.getName())
				.signWith(key).compact();

		return AuthResponseDto.builder().userId(authentication.getName()).accessToken(accessToken)
				.refreshToken(refreshToken).build();
	}

	public Authentication getAuthentication(String accessToken) {
		// 토큰 복호화
		Claims claims = parseJwtClaims(accessToken);

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get("AUTHORITIES").toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
	}

	private Claims parseJwtClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody();
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String TOKEN_HEADER = "Authorization";
		String TOKEN_PREFIX = "Bearer ";
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		String TOKEN_HEADER = "Refresh";
		String TOKEN_PREFIX = "Bearer ";
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public String generateNewAccessToken(String accessToken, String refreshToken,HttpServletResponse response) {

		// 리프레쉬 토큰 만료 여부 확인
		if (tokenExpired(refreshToken)) {
			// 토큰이 만료된 경우 예외 처리 등의 로직 추가
			throw new RuntimeException("refreshToken token has expired");
		}

		long EXPIRE_DURATION = 1000L * 60 * 60;
		Date issuedAt = new Date();
		Date expiration = new Date(issuedAt.getTime() + TimeUnit.MINUTES.toMillis(EXPIRE_DURATION));
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		// 새로운 액세스 토큰 생성 로직 구현
		Claims claims = parseJwtClaims(accessToken);
		// 예: 새로운 JWT 토큰 생성 및 반환
		String newAccessToken = Jwts.builder().setIssuedAt(issuedAt).setExpiration(expiration)
				.claim("AUTHORITIES", claims.get("AUTHORITIES").toString()).setSubject(claims.getSubject())
				.signWith(key).compact();
		response.setHeader("Authorization", newAccessToken);
		
		return newAccessToken;
	}

	public boolean tokenExpired(String token) {
		Claims claims = parseJwtClaims(token);

		// 토큰 만료 여부 확인
		Date expiration = claims.getExpiration();
		return expiration.after(new Date());
	}
}
