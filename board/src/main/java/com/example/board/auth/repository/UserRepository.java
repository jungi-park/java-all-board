package com.example.board.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
