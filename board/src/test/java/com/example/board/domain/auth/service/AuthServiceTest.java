package com.example.board.domain.auth.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.board.domain.auth.dto.AuthRequestDto;
import com.example.board.domain.auth.dto.AuthResponseDto;
import com.example.board.domain.auth.dto.SignUpRequestDto;
import com.example.board.domain.auth.entity.User;
import com.example.board.global.security.provider.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AuthServiceTest {

	@InjectMocks
	private AuthServiceImpl authService;

	@Mock
	private BCryptPasswordEncoder encoder;

	@Mock
	private UserService userService;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@Mock
	private AuthenticationManager authenticationManager;

	@Test
	@DisplayName("회원 생성 테스트")
	void createUserTest() throws Exception {
		// given
		SignUpRequestDto signUpDto = new SignUpRequestDto("박준기", "qmqqqm", "123456!");

		when(encoder.encode(signUpDto.getPassword())).thenReturn("encodedPassword");
		doNothing().when(userService).saveUser(any(User.class));

		// when
		authService.createUser(signUpDto);

		// then
		verify(encoder).encode(signUpDto.getPassword());
		verify(userService).saveUser(any(User.class));
	}

	@Test
	@DisplayName("회원 로그인 테스트")
	void loginTest() throws Exception {
		// given
		AuthRequestDto authRequestDto = AuthRequestDto.builder().userId("qmqqqm").password("123456!").build();

		Authentication authentication = Mockito.mock(Authentication.class);

		// 인증 매니저 모킹
		when(authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDto.getUserId(), authRequestDto.getPassword())))
				.thenReturn(authentication);

		// JWT 토큰 생성 모킹
		when(jwtTokenProvider.createToken(authentication))
				.thenReturn(AuthResponseDto.builder().accessToken("access").refreshToken("refrash").build());

		// when
		AuthResponseDto result = authService.login(authRequestDto);

		// then
		Assertions.assertThat(result.getAccessToken()).isEqualTo("access");
		Assertions.assertThat(result.getRefreshToken()).isEqualTo("refrash");
		verify(authenticationManager).authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDto.getUserId(), authRequestDto.getPassword()));
		verify(jwtTokenProvider).createToken(authentication);
	}
}
