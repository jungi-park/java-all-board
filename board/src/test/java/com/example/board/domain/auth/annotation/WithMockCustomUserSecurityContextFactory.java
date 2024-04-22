package com.example.board.domain.auth.annotation;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		Collection<? extends GrantedAuthority> authorities = Arrays.stream(customUser.role().split(""))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		Authentication auth = new UsernamePasswordAuthenticationToken(customUser.userId(), null, authorities);
		context.setAuthentication(auth);
		return context;
	}
}