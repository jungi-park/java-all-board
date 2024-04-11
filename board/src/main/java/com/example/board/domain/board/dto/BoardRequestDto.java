package com.example.board.domain.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "글쓰기 요청 데이터")
@Data
public class BoardRequestDto {
	
	@Schema(description = "제목", example = "안녕하세요 첫 글이에요")
	private String title;
	
	@Schema(description = "내용", example = "글쓰기가 너무 재밌어요 다음에 또 쓸게요")
	private String content;
	
}
