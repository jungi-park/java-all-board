package com.example.board.board.service;

import com.example.board.board.dto.BoardRequestDto;

public interface BoardService {

	void writeBoard(BoardRequestDto boardRequestDto, String userId) throws Exception;

}
