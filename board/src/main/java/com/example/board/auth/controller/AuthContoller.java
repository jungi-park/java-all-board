package com.example.board.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.entity.User;
import com.example.board.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
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

}