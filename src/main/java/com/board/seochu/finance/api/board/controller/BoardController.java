package com.board.seochu.finance.api.board.controller;

import com.board.seochu.finance.api.board.dto.BoardDto;
import com.board.seochu.finance.api.board.service.BoardService;
import com.board.seochu.finance.api.login.dto.SignDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @ResponseBody
    @GetMapping("/boardList")
    public ResponseEntity<HashMap<String,Object>> getBoardList(@Valid BoardDto boardDto) throws UnsupportedEncodingException {
        return ResponseEntity.ok().body(boardService.boardList(boardDto));
    }

    @PostMapping("/boardWrite")
    public ResponseEntity<Integer> writeBoard(@Valid @RequestBody BoardDto boardDto) {
        return ResponseEntity.ok().body(boardService.writeBoard(boardDto));
    }
}
