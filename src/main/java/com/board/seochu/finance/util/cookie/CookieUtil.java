package com.board.seochu.finance.util.cookie;

import com.board.seochu.finance.util.auth.jwt.JwtProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {

    public Cookie createCookies(String cookieName, String value) {
        Cookie token = new Cookie(cookieName, value);
        token.setHttpOnly(true);
        token.setMaxAge((int) JwtProvider.TOKEN_VALIDATION_SECOND);
        token.setPath("/");

        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName) {
        final Cookie[] cookies = req.getCookies();
        if(cookies == null) return null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName))
                return cookie;
        }

        return null;
    }
}
