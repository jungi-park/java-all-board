package com.example.board.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.dto.SignUpDto;
import com.example.board.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "권한 API", description = "회원가입 및 로그인 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthContoller {

	private final AuthService authService;

	@Operation(summary = "로그인 API", description = "로그인 API입니다.")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) throws Exception {
		AuthResponseDto response = authService.login(authRequestDto);
		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("로그인 성공");
		return response;
	}
	
	@Operation(summary = "회원가입 API", description = "회원가입 API입니다.")
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public AuthResponseDto signUp(@RequestBody SignUpDto signUpDto) throws Exception {
		authService.createUser(signUpDto);
		AuthResponseDto response = new AuthResponseDto();
		response.setMemberId(signUpDto.getUserId());
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