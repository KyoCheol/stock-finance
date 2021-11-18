package com.board.seochu.finance.util.auth.jwt;

import com.board.seochu.finance.common.consts.StcConst;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = StcConst.BEARER_TYPE;

    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }
}
