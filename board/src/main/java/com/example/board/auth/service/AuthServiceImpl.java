package com.example.board.auth.service;

import org.springframework.stereotype.Service;

import com.example.board.auth.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private final UserService userService;

	@Override
	public void createUser(User user) throws Exception {
		userService.saveUser(user);
	}

	@Override
	public void login(User user) throws Exception {

	}

}
