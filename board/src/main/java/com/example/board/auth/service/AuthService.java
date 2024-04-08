package com.example.board.auth.service;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.dto.SignUpRequestDto;

public interface AuthService {
	
	public void createUser(SignUpRequestDto signUpDto) throws Exception;
	
	public AuthResponseDto login(AuthRequestDto authRequestDto) throws Exception;
}
