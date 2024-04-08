package com.example.board.auth.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class SignUpResponseDto {

	private String message;

	private String userId;

	private HttpStatus httpStatus;
}
