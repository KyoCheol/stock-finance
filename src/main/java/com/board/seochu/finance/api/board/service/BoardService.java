package com.board.seochu.finance.api.board.service;

import com.board.seochu.finance.api.board.dto.BoardDto;
import com.board.seochu.finance.api.board.dto.LikeDto;
import com.board.seochu.finance.api.board.dto.ReplyDto;
import com.board.seochu.finance.api.board.mapper.BoardMapper;
import com.board.seochu.finance.config.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public HashMap<String, Object> boardList(BoardDto boardDto) throws UnsupportedEncodingException {

        int rows = Integer.parseInt(boardDto.getRows());
        int page = Integer.parseInt(boardDto.getPage());

        String[] sort = URLDecoder.decode(boardDto.getSort(), "UTF-8").split(",");

        boardDto.setStartNo(((page * rows) - rows) + 1);
        boardDto.setEndNo(page * rows);
        boardDto.setConvertSort(sort);

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

    public HashMap<String, Object> boardDetail(int docNo, int userNo) {
        boardMapper.increaseCount(docNo);

        LikeDto likeDto = new LikeDto();
        likeDto.setBoardno(docNo);
        likeDto.setMno(userNo);

        LikeDto likeData  = boardMapper.countByLike(likeDto);

        int likeCheck = 0;
        int unLikeCheck = 0;

        if(ObjectUtils.isEmpty(likeData)) {
            likeCheck = 0;
            unLikeCheck = 0;
        } else {
            if(likeData.getLike_check() == 1) {
                likeCheck = 1;
            } else {
                likeCheck = 0;
            }

            if(likeData.getUnLike_check() == 1) {
                unLikeCheck = 1;
            } else {
                unLikeCheck = 0;
            }
        }
        BoardDto boardView = boardMapper.selectBoardDetail(docNo);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("likeCheck", likeCheck);
        map.put("unLikeCheck", unLikeCheck);
        map.put("data", boardView);

        return map;
    }

    public List<ReplyDto> boardReplyList(int docNo) {
        return boardMapper.getReplyList(docNo);
    }

    public int replyInsert(ReplyDto replyDto) {
        return boardMapper.insertReply(replyDto);
    }

    public int replyUpdate(ReplyDto replyDto) {
        return boardMapper.updateReply(replyDto);
    }

    public int replyDelete(ReplyDto replyDto) {
        return boardMapper.deleteReply(replyDto);
    }

    public HashMap<String, Object> clickLike(int docNo, int userNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        LikeDto likeDto = new LikeDto();
        likeDto.setBoardno(docNo);
        likeDto.setMno(userNo);

        try {
            LikeDto likeData = boardMapper.likeRead(likeDto);

            if(ObjectUtils.isEmpty(likeData)) {
                // 1
                boardMapper.insertLike(likeDto);
                boardMapper.likeCntUpByBoard(docNo);
            } else {
                int like_check = likeData.getLike_check(); // 좋아요 체크 값
                if(like_check == 0) {
                    // 1
                    boardMapper.checkLike(likeDto);
                    boardMapper.likeCntUpByBoard(docNo);
                } else {
                    // 0
                    boardMapper.checkUnLike(likeDto);
                    boardMapper.likeCntDownByBoard(docNo);
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        BoardDto boardData = boardMapper.selectBoardDetail(docNo);
        int like_cnt = boardData.getLike_cnt(); //   // 총갯수

        LikeDto likeData  = boardMapper.countByLike(likeDto); // 자기가 눌럿는지 확인 값

        int likeCheck = 0;
        if(ObjectUtils.isEmpty(likeData)) {
            likeCheck = 0;
        } else {
            if(likeData.getLike_check() == 1) {
                likeCheck = 1;
            } else {
                likeCheck = 0;
            }
        }

        map.put("likeCnt", like_cnt);
        map.put("likeCheck", likeCheck);

        return map;
    }

    public HashMap<String, Object> clickUnLike(int docNo, int userNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        LikeDto likeDto = new LikeDto();
        likeDto.setBoardno(docNo);
        likeDto.setMno(userNo);

        try {
            LikeDto likeData = boardMapper.likeRead(likeDto);

            if(ObjectUtils.isEmpty(likeData)) {
                boardMapper.insertLike(likeDto);
                boardMapper.unLikeCntUpByBoard(docNo);
            } else {
                int unLikeCheck = likeData.getUnLike_check();
                if(unLikeCheck == 0) {
                    // 1
                    boardMapper.checkByLike(likeDto);
                    boardMapper.unLikeCntUpByBoard(docNo);
                } else {
                    // 0
                    boardMapper.checkByUnLike(likeDto);
                    boardMapper.unLikeCntUpByBoard(docNo);
                }
            }
        } catch(Exception e) {
            log.info(e.getMessage());
        }

        BoardDto boardData = boardMapper.selectBoardDetail(docNo);
        int unLike_cnt = boardData.getUnLike_cnt(); //   // 총갯수

        LikeDto likeData  = boardMapper.countByLike(likeDto); // 자기가 눌럿는지 확인 값

        int unLikeCheck = 0;
        if(ObjectUtils.isEmpty(likeData)) {
            unLikeCheck = 0;
        } else {
            if(likeData.getLike_check() == 1) {
                unLikeCheck = 1;
            } else {
                unLikeCheck = 0;
            }
        }

        map.put("unLikeCnt", unLike_cnt);
        map.put("unLikeCheck", unLikeCheck);

        return map;
    }
}
