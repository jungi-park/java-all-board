package com.example.board.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.dto.SignUpRequestDto;
import com.example.board.auth.dto.SignUpResponseDto;
import com.example.board.auth.entity.User;
import com.example.board.auth.service.AuthService;
import com.example.board.auth.service.UserService;

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
	private final UserService userService;

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
	public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpDto) throws Exception {
		authService.createUser(signUpDto);
		SignUpResponseDto response = new SignUpResponseDto();
		response.setUserId(signUpDto.getUserId());
		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("회원가입 성공");
		return response;
	}

	@Operation(summary = "개인정보 확인 API", description = "개인정보 확인 API입니다.")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public User userInfo (Authentication authentication) throws Exception {
		String userId = (String) authentication.getPrincipal();
		log.info("userDetails = {}", userId);
		return userService.findByUserId(userId);
	}

}