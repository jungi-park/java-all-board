package com.example.board.domain.board.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.board.domain.auth.annotation.WithMockCustomUser;
import com.example.board.domain.board.dto.BoardRequestDto;
import com.example.board.domain.board.service.BoardService;
import com.example.board.global.security.filter.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = BoardController.class, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class), })
public class BoardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BoardService boardService;

	@Test
	@WithMockCustomUser
	@DisplayName("controller 글쓰기 테스트")
	public void writeBoardTest() throws Exception {
		// given
		String userId = "qmqqqm";
		BoardRequestDto boardRequestDto = new BoardRequestDto();
		boardRequestDto.setContent("컨텐츠");
		boardRequestDto.setTitle("타이틀");

		doNothing().when(boardService).writeBoard(any(BoardRequestDto.class), any(String.class));

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/board").contentType(MediaType.APPLICATION_JSON)
				.with(csrf()).content(new ObjectMapper().writeValueAsString(boardRequestDto)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.userId").value("qmqqqm"))
				.andExpect(jsonPath("$.message").value("글쓰기 성공"));
	}

}
