package com.board.seochu.finance.api.board.mapper;

import com.board.seochu.finance.api.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {

    /**
     * 게시판 목록 조회
     *
     * @param vo
     * @return
     */
    public List<BoardDto> getBoardList(@Param("boardDto") BoardDto boardDto);

    /**
     * 게시판 건수 조회
     * @param vo
     * @return
     */
    int selectBoardListCount(@Param("boardDto") BoardDto boardDto);

    /**
     * 게시글 등록
     * @param vo
     * @return
     */
    int insertBoard(@Param("boardDto") BoardDto boardDto);
}
