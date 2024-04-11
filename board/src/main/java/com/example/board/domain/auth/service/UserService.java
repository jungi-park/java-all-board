package com.example.board.domain.auth.service;

import java.util.List;
import java.util.Optional;

import com.example.board.domain.auth.entity.User;

public interface UserService {
	Optional<User> findUserById(Long id) throws Exception;

	List<User> findAllUsers() throws Exception;
	
	void saveUser(User user) throws Exception;
	
	Optional<User> findByUserId(String userId);
	
}
