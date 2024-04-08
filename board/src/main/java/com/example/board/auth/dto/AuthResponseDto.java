package com.example.board.auth.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthResponseDto {
	
	private String message;
	private String accessToken;
    private String refreshToken;
    private String userId;
    private HttpStatus httpStatus;
}
