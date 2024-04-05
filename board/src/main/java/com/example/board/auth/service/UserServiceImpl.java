package com.example.board.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.board.auth.entity.User;
import com.example.board.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public Optional<User> findUserById(Long id) throws Exception {
		return userRepository.findById(id);
	}

	@Override
	public List<User> findAllUsers() throws Exception {
		return userRepository.findAll();
	}

	@Override
	public void saveUser(User user) throws Exception {
		userRepository.save(user);
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

}
