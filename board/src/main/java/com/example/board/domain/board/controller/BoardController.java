package com.example.board.domain.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.board.dto.BoardRequestDto;
import com.example.board.domain.board.dto.BoardResponseDto;
import com.example.board.domain.board.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "게시판 API", description = "게시판 API")
@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
@Slf4j
public class BoardController {

	private final BoardService boardService;

	@Operation(summary = "글쓰기 API", description = "게시판 글쓰기 API입니다.")
	@RequestMapping(method = RequestMethod.POST)
	public BoardResponseDto writeBoard(@RequestBody BoardRequestDto boardRequestDto, Authentication authentication)
			throws Exception {
		String userId = (String) authentication.getPrincipal();
		boardService.writeBoard(boardRequestDto, userId);
		BoardResponseDto response = new BoardResponseDto();
		response.setUserId(userId);
		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("글쓰기 성공");
		return response;
	}

	@Operation(summary = "글삭제 API", description = "게시판 글삭제 API입니다.")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public BoardResponseDto deleteBoard(@PathVariable("id") Long boardId, Authentication authentication) {
		String userId = (String) authentication.getPrincipal();
		boardService.deleteBoard(boardId, userId);
		log.info("boardId = {} , userId = {}",boardId,userId);
		return null;
	}

}
