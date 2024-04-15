package com.example.board.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 요청 데이터")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
	
	@Schema(description = "아이디", example = "qmqqqm")
	private String userId;

	@Schema(description = "이름", example = "박준기")
	private String name;

	@Schema(description = "비밀번호", example = "123456!")
	private String password;
}
