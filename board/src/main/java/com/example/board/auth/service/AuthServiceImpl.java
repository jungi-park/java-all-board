package com.example.board.auth.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.entity.User;
import com.example.board.auth.model.AuthRole;
import com.example.board.security.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private final UserService userService;
	private final JwtTokenProvider JwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder encoder;

	@Override
	public void createUser(User user) throws Exception {
		user.setPassword(encoder.encode(user.getPassword()));
		Collection<AuthRole> userRole = Collections.singleton(AuthRole.USER);
		user.setRoles(userRole);
		userService.saveUser(user);
	}

	@Override
	public AuthResponseDto login(AuthRequestDto authRequestDto) throws Exception {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUserId(), authRequestDto.getPassword()));
		return JwtTokenProvider.createToken(authentication);
	}


}
