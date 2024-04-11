package com.example.board.domain.board.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "글쓰기 응답 데이터")
@Data
public class BoardResponseDto {
	
	@Schema(description = "메세지")
	private String message;
	
	@Schema(description = "로그인한 유저 아이디")
    private String userId;
	
	@Schema(description = "응답코드")
    private HttpStatus httpStatus;

}
