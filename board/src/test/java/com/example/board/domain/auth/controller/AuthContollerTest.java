package com.example.board.domain.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.example.board.domain.auth.dto.AuthRequestDto;
import com.example.board.domain.auth.dto.AuthResponseDto;
import com.example.board.domain.auth.dto.SignUpRequestDto;
import com.example.board.domain.auth.service.AuthService;
import com.example.board.domain.auth.service.UserService;
import com.example.board.global.security.filter.JwtAuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = AuthContoller.class, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthorizationFilter.class), })
public class AuthContollerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	private UserService userService;

	@Test
	@WithMockUser
	@DisplayName("회원가입 확인")
	public void signUpTest() throws Exception {
		// given
		SignUpRequestDto signUpDto = SignUpRequestDto.builder().userId("qmqqqm").name("박준기").password("123456!")
				.build();

		doNothing().when(authService).createUser(any(SignUpRequestDto.class));

		// when
		ResultActions resultActions = mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
				.with(csrf()).content(new ObjectMapper().writeValueAsString(signUpDto)));

//	 // MvcResult 객체를 통해 응답 내용을 문자열로 가져오기
//	    MvcResult result = resultActions.andReturn();
//	    String contentAsString = result.getResponse().getContentAsString(); // 변수 선언 및 초기화
//
//	    // JsonPath를 사용하여 userId 값 추출
//	    String userId = JsonPath.parse(contentAsString).read("$.userId");
//
//	    // 추출한 userId 값 로깅
//	    log.info("회원가입된 userId: {}", userId);

		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.userId").value("qmqqqm"))
				.andExpect(jsonPath("$.message").value("회원가입 성공"));
	}

	@Test
	@WithMockUser
	@DisplayName("로그인 확인")
	public void loginTest() throws Exception {
		// given
		AuthRequestDto authRequestDto = AuthRequestDto.builder().userId("qmqqqm").password("123456!").build();

		when(authService.login(authRequestDto))
				.thenReturn(AuthResponseDto.builder().accessToken("access").refreshToken("refrash").build());
		// when
		ResultActions resultActions = mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
				.with(csrf()).content(new ObjectMapper().writeValueAsString(authRequestDto)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.accessToken").value("access"))
				.andExpect(jsonPath("$.refreshToken").value("refrash"));

	}

}
