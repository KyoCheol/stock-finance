package com.board.seochu.finance.api.board.controller;

import com.board.seochu.finance.api.board.dto.BoardDto;
import com.board.seochu.finance.api.board.dto.ReplyDto;
import com.board.seochu.finance.api.board.service.BoardService;
import com.board.seochu.finance.api.login.dto.SignDTO;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

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

    @ResponseBody
    @GetMapping("/boardDetail/{docNo}/{userNo}")
    public ResponseEntity<HashMap<String, Object>> getBoardDetail(
            @ApiParam(value = "docNo", required = true) @PathVariable int docNo,
            @ApiParam(value = "userNo", required = true) @PathVariable int userNo) {
        return ResponseEntity.ok().body(boardService.boardDetail(docNo, userNo));
    }

    @ResponseBody
    @GetMapping("/reply/list/{docNo}")
    public ResponseEntity<List<ReplyDto>> getBoardReplyList(
            @ApiParam(value = "docNo", required = true) @PathVariable int docNo) {
        return ResponseEntity.ok().body(boardService.boardReplyList(docNo));
    }

    @PostMapping("/reply/insert")
    public ResponseEntity<Integer> replyInsert(@Valid @RequestBody ReplyDto replyDto) {
        return ResponseEntity.ok().body(boardService.replyInsert(replyDto));
    }

    @PostMapping("/reply/update")
    public ResponseEntity<Integer> replyUpdate(@Valid @RequestBody ReplyDto replyDto) {
        return ResponseEntity.ok().body(boardService.replyUpdate(replyDto));
    }

    @PostMapping("/reply/delete")
    public ResponseEntity<Integer> replyDelete(@Valid @RequestBody ReplyDto replyDto) {
        return ResponseEntity.ok().body(boardService.replyDelete(replyDto));
    }

    @PostMapping("/like/{docNo}/{userNo}")
    public ResponseEntity<HashMap<String, Object>> clickLike( @ApiParam(value = "docNo", required = true) @PathVariable int docNo,
                                                @ApiParam(value = "userNo", required = true) @PathVariable int userNo) {
        return ResponseEntity.ok().body(boardService.clickLike(docNo, userNo));
    }

    @PostMapping("/unLike/{docNo}/{userNo}")
    public ResponseEntity<HashMap<String, Object>> clickUnLike( @ApiParam(value = "docNo", required = true) @PathVariable int docNo,
                                                                @ApiParam(value = "userNo", required = true) @PathVariable int userNo) {
        return ResponseEntity.ok().body(boardService.clickUnLike(docNo, userNo));
    }
}
