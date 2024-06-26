package com.example.board.domain.board.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.auth.entity.User;
import com.example.board.domain.auth.service.UserService;
import com.example.board.domain.board.dto.BoardRequestDto;
import com.example.board.domain.board.entity.Board;
import com.example.board.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final UserService userService;

	@Override
	@Transactional
	public void writeBoard(BoardRequestDto boardRequestDto, String userId) {
		User writer = userService.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
		Board board = Board.builder().title(boardRequestDto.getTitle()).content(boardRequestDto.getContent())
				.writer(writer).build();
		boardRepository.save(board);
	}

	@Override
	@Transactional
	public void deleteBoard(Long boardId, String userId) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new RuntimeException("해당하는 게시글을 찾을 수 없습니다."));
		if (board.getWriter().getUserId().equals(userId)) {
			board.deleteUpdate(true);
			boardRepository.save(board);
		}
	}

	@Override
	@Transactional
	public void updateBoard(BoardRequestDto boardRequestDto, Long boardId, String userId) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new RuntimeException("해당하는 게시글을 찾을 수 없습니다."));
		if (board.getWriter().getUserId().equals(userId)) {
			board.contentUpdate(boardRequestDto.getContent());
			board.titleUpdate(boardRequestDto.getTitle());
			boardRepository.save(board);
		}
	}

}
