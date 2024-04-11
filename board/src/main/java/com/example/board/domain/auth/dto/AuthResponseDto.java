package com.example.board.domain.auth.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 응답 데이터")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthResponseDto {
	
	@Schema(description = "메세지")
	private String message;
	
	@Schema(description = "엑세스 토큰")
	private String accessToken;
	
	@Schema(description = "리프레시 토큰")
    private String refreshToken;
	
	@Schema(description = "로그인한 유저 아이디")
    private String userId;
	
	@Schema(description = "응답코드")
    private HttpStatus httpStatus;
}
