package com.example.board.auth.service;

import com.example.board.auth.entity.User;

public interface AuthService {
	public void createUser(User user) throws Exception;
	
	public void login(User user) throws Exception;
}
