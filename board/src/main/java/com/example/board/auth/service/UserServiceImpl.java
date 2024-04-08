package com.example.board.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.auth.entity.User;
import com.example.board.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	@Transactional
	public Optional<User> findUserById(Long id) throws Exception {
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public List<User> findAllUsers() throws Exception {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public void saveUser(User user) throws Exception {
		userRepository.save(user);
	}

	@Override
	@Transactional
	public Optional<User> findByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

}
