package com.example.board.auth.service;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.dto.SignUpDto;

public interface AuthService {
	
	public void createUser(SignUpDto signUpDto) throws Exception;
	
	public AuthResponseDto login(AuthRequestDto authRequestDto) throws Exception;
}
