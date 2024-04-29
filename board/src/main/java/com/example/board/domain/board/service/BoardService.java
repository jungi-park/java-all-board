package com.example.board.domain.board.service;

import com.example.board.domain.board.dto.BoardRequestDto;

public interface BoardService {

	void writeBoard(BoardRequestDto boardRequestDto, String userId) throws Exception;

	void deleteBoard(Long boardId, String userId);

	void updateBoard(BoardRequestDto boardRequestDto, Long boardId, String userId);

}
