package com.example.board.auth.service;

import com.example.board.auth.dto.AuthRequestDto;
import com.example.board.auth.dto.AuthResponseDto;
import com.example.board.auth.entity.User;

public interface AuthService {
	public void createUser(User user) throws Exception;
	
	public AuthResponseDto login(AuthRequestDto authRequestDto) throws Exception;
}
