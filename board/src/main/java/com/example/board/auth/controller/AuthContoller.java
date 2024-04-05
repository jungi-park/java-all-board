package com.example.board.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.entity.User;
import com.example.board.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthContoller {

	private final AuthService authService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) throws Exception {
		AuthResponseDto response = authService.login(authRequestDto);
		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("로그인 성공");
		return response;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public AuthResponseDto signUp(@RequestBody User user) throws Exception {
		authService.createUser(user);
		AuthResponseDto response = new AuthResponseDto();
		response.setMemberId(user.getUserId());
		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("회원가입 성공");
		return response;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public AuthResponseDto signUp(Authentication authentication) throws Exception {
		Object userDetails = authentication;
		log.info("userDetails = {}", userDetails);
		AuthResponseDto response = new AuthResponseDto();
		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("필터테스트 성공");
		return response;
	}

}