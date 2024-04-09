package com.example.board.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "게시판 API", description = "게시판 API")
@RequestMapping("/api/board")
@RestController
public class BoardController {

}
