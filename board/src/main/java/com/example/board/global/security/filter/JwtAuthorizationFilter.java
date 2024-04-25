package com.example.board.global.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.board.global.security.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String accessToken = jwtTokenProvider.resolveAccessToken(request);
		String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
		if (accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}
//		엑세스 토큰 만료시
		if (jwtTokenProvider.tokenExpired(accessToken)) {
			accessToken = jwtTokenProvider.generateNewAccessToken(accessToken,refreshToken,response);
		}
		try {
			Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
			log.info("GRANTED_AUTHENTICATION={}", authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		filterChain.doFilter(request, response);
	}
}
