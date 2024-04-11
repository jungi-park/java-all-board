package com.example.board.board.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.auth.entity.User;
import com.example.board.auth.service.UserService;
import com.example.board.board.dto.BoardRequestDto;
import com.example.board.board.entity.Board;
import com.example.board.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final UserService userService;

	@Override
	public void writeBoard(BoardRequestDto boardRequestDto, String userId) {
		User writer = userService.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
		Board board = Board.builder().title(boardRequestDto.getTitle()).content(boardRequestDto.getContent()).writer(writer).build();
		boardRepository.save(board);
	}

}
