package com.board.seochu.finance.api.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LikeDto {
    private int likeno;
    private int boardno;
    private int mno;
    private int like_check;
    private int unLike_check;
}
