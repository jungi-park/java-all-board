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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
}
