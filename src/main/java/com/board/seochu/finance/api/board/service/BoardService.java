package com.board.seochu.finance.api.board.service;

import com.board.seochu.finance.api.board.dto.BoardDto;
import com.board.seochu.finance.api.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public HashMap<String, Object> boardList(BoardDto boardDto) throws UnsupportedEncodingException {

        int rows = Integer.parseInt(boardDto.getRows());
        int page = Integer.parseInt(boardDto.getPage());

        // WRITER%20asc%2CREG_DTTM%20asc
        // WRITER%20asc%2CREG_DTTM%20asc%2CVIEW%20asc
        // order by test desc,
//        for(String str : array) {
//            log.info("str > > > > > ");
//            log.info(str);
//            //log.info(str.replace("%20", ""));
//        }

        String[] test2 = URLDecoder.decode(boardDto.getSort(), "UTF-8").split(",");
        for(String str : test2) {
            log.info(" s t r > > > ");
            log.info(str);
        }
//        String[] test = boardDto.getSort();
//        for(String str : test) {
//            log.info("str > > > > ");
//            log.info(str);
//            log.info("test > > >" + URLDecoder.decode(str, "UTF-8").split(","));
//        }
        boardDto.setStartNo(((page * rows) - rows) + 1);
        boardDto.setEndNo(page * rows);
        boardDto.setTest(test2);


        List<BoardDto> result = boardMapper.getBoardList(boardDto);

        int total = 0;

        if(result.size() > 0) {
            total = boardMapper.selectBoardListCount(boardDto);
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("data", result);
        map.put("total", total);

        return map;
    }

    public int writeBoard(BoardDto boardDto) {
        boardDto.setWriter("seochu");
        int result = 0;

        int rows = boardMapper.insertBoard(boardDto);

        if(rows > 0) {
            result = boardDto.getDocNo();
        }
        return result;
    }
}
