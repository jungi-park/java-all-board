package com.example.board.auth.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponseDto {
	
	private String message;
	private String accessToken;
    private String refreshToken;
    private String memberId;
    private HttpStatus httpStatus;
}
