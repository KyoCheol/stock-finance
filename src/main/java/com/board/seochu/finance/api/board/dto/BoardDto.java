package com.board.seochu.finance.api.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BoardDto {

    private int docNo;
    private String title;
    private String content;
    private String writer;
    private String regDttm;
    private int view;
    private int reply;

    private int startNo;
    private int endNo;
    private String sort;

    private String schType;
    private String schVal;

    private String rows;
    private String page;

   private String[] test;
}
