package com.example.board.auth.dto;

import lombok.Data;

@Data
public class SignUpDto {
	
	private String userId;

	private String name;

	private String password;
}
