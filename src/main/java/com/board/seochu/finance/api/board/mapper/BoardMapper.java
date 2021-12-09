package com.board.seochu.finance.api.board.mapper;

import com.board.seochu.finance.api.board.dto.BoardDto;
import com.board.seochu.finance.api.board.dto.LikeDto;
import com.board.seochu.finance.api.board.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
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
    List<BoardDto> getBoardList(@Param("boardDto") BoardDto boardDto);

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

    /**
     * 조회수 증가
     * @param vo
     * @throws Exception
     */
    void increaseCount(@Param("docNo") int docNo);

    /**
     * 게시글 조회
     * @param vo
     * @return
     * @throws Exception
     */
    BoardDto selectBoardDetail(@Param("docNo") int docNo);

    /**
     * 댓글 조회
     * @param vo
     * @return
     * @throws Exception
     */
    List<ReplyDto> getReplyList(@Param("docNo") int docNo);

    /**
     * 댓글 작성
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int insertReply(@Param("replyDto") ReplyDto replyDto);

    /**
     * 댓글 수정
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int updateReply(@Param("replyDto") ReplyDto replyDto);

    /**
     * 댓글 삭제
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int deleteReply(@Param("replyDto") ReplyDto replyDto);

    /**
     * 게시판의 좋아요 번호가 있는지 카운트
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    LikeDto countByLike(@Param("likeDto") LikeDto likeDto);

    /**
     * 좋아요 등록
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int insertLike(@Param("likeDto") LikeDto likeDto);

    /**
     * 좋아요 체크 여부 (0 -> 1)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int checkLike(@Param("likeDto") LikeDto likeDto);

    /**
     * 좋아요 체크 여부 (1 -> 0)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int checkUnLike(@Param("likeDto") LikeDto likeDto);

    /**
     * 조회
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    LikeDto likeRead(@Param("likeDto") LikeDto likeDto);

    /**
     * 해당 게시물 좋아여 업
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    void likeCntUpByBoard(@Param("docNo") int DocNo);

    /**
     * 해당 게시물 좋아여 다운
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    void likeCntDownByBoard(@Param("docNo") int DocNo);

    /**
     * 해당 게시물 싫어요 업
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    void unLikeCntUpByBoard(@Param("docNo") int DocNo);

    /**
     * 해당 게시물 싫어요 다운
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    void unLikeCntDownByBoard(@Param("docNo") int DocNo);

    /**
     * 싫어요 체크 여부 (0 -> 1)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int checkByLike(@Param("likeDto") LikeDto likeDto);

    /**
     * 싫어요 체크 여부 (1 -> 0)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    int checkByUnLike(@Param("likeDto") LikeDto likeDto);
}
