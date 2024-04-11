package com.example.board.domain.auth.service;

import com.example.board.domain.auth.dto.AuthRequestDto;
import com.example.board.domain.auth.dto.AuthResponseDto;
import com.example.board.domain.auth.dto.SignUpRequestDto;

public interface AuthService {
	
	public void createUser(SignUpRequestDto signUpDto) throws Exception;
	
	public AuthResponseDto login(AuthRequestDto authRequestDto) throws Exception;
}
