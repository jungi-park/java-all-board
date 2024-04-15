package com.example.board.domain.auth.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 응답 데이터")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {

	@Schema(description = "메시지")
	private String message;

	@Schema(description = "회원가입된 유저 아이디")
	private String userId;

	@Schema(description = "응답코드")
	private HttpStatus httpStatus;
}
