package com.board.seochu.finance.util.auth;

import com.board.seochu.finance.common.consts.StcConst;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class AuthProvider {

    @Value("${jwt.secret.signature}")
    private String signatureKey;

    @PostConstruct
    protected void init() {
        signatureKey = Base64.getEncoder().encodeToString(signatureKey.getBytes());
    }

    private final UserDetailsService userDetailsService;

    /**
     * @throws Exception
     * @method 설명 : jwt 토큰 발급
     */
    public String createToken(
            Long id,
            String username,
            String role) {

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + StcConst.ACCESS_TOKEN_EXPIRE_TIME);

        final JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setExpiration(accessTokenExpiresIn)
                .claim(StcConst.AUTHORITIES_KEY, role)
                .claim(StcConst.ACCESS_USER_ID, id)
                .signWith(SignatureAlgorithm.HS256, signatureKey);

        return StcConst.BEARER_TYPE + builder.compact();
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * @method 설명 : 컨텍스트에 해당 유저에 대한 권한을 전달하고 API 접근 전 접근 권한을 확인하여 접근 허용 또는 거부를 진행한다.
     */
    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {

        // 토큰 기반으로 유저의 정보 파싱
        Claims claims = Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody();

        String username = claims.getSubject();
        long id = claims.get(StcConst.ACCESS_USER_ID, Integer.class);
        String role = claims.get(StcConst.AUTHORITIES_KEY, String.class);

        CustomUserDetails userDetails = new CustomUserDetails(id, username, role);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * @method 설명 : request객체 헤더에 담겨 있는 토큰 가져오기
     */
    public String resolveToken(HttpServletRequest request) {
        if (request.getHeader("accesstoken") == null)
            return null;

        return request.getHeader("accesstoken").replace(StcConst.BEARER_TYPE, "");
    }

    /**
     * @method 설명 : 토큰 유효시간 만료여부 검사 실행
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
