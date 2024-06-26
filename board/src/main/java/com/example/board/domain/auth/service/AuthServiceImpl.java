package com.example.board.domain.auth.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.auth.dto.AuthRequestDto;
import com.example.board.domain.auth.dto.AuthResponseDto;
import com.example.board.domain.auth.dto.SignUpRequestDto;
import com.example.board.domain.auth.entity.Role;
import com.example.board.domain.auth.entity.User;
import com.example.board.domain.auth.model.RoleType;
import com.example.board.global.security.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

	private final UserService userService;
	private final JwtTokenProvider JwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder encoder;

	@Override
	@Transactional
	public void createUser(SignUpRequestDto signUpDto) throws Exception {
		User user = User.builder().name(signUpDto.getName()).userId(signUpDto.getUserId())
				.password(encoder.encode(signUpDto.getPassword())).build();
		Collection<Role> userRole = Collections.singleton(Role.builder().roleName(RoleType.USER).user(user).build());
		user.updateRole(userRole);
		userService.saveUser(user);
	}

	@Override
	public AuthResponseDto login(AuthRequestDto authRequestDto) throws Exception {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDto.getUserId(), authRequestDto.getPassword()));
		return JwtTokenProvider.createToken(authentication);
	}

}
