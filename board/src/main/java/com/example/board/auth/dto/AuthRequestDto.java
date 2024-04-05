package com.example.board.auth.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
	private String userId;
    private String password;
}
