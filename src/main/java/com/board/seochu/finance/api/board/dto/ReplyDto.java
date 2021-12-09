package com.board.seochu.finance.api.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReplyDto {
    private int replyNo;
    private int docNo;
    private String writer;
    private String content;
    private String regDttm;
}
