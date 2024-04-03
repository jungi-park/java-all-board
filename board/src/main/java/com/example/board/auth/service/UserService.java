package com.example.board.auth.service;

import java.util.List;
import java.util.Optional;

import com.example.board.auth.entity.User;

public interface UserService {
	Optional<User> findUserById(long id) throws Exception;

	List<User> findAllUsers() throws Exception;
	
	void saveUser(User user) throws Exception;
	
}
