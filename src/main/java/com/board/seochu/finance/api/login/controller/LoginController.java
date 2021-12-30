package com.board.seochu.finance.api.login.controller;

import com.board.seochu.finance.api.login.dto.AuthenticationDto;
import com.board.seochu.finance.api.login.dto.LoginDto;
import com.board.seochu.finance.api.login.dto.SignDto;
import com.board.seochu.finance.api.login.service.LoginService;
import com.board.seochu.finance.api.role.domain.repository.RoleRepository;
import com.board.seochu.finance.api.user.domain.entity.User;
import com.board.seochu.finance.api.user.domain.repository.UserRepository;
import com.board.seochu.finance.api.user.service.EmailService;
import com.board.seochu.finance.util.auth.jwt.JwtProvider;
import com.board.seochu.finance.util.cookie.CookieUtil;
import com.board.seochu.finance.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    LoginService loginService;

    @Autowired
    EmailService emailService;

    @Autowired
    CookieUtil cookieUtil;

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationDto> authenticateUser(@Valid @RequestBody LoginDto loginRequest,
                                                              HttpServletRequest req,
                                                              HttpServletResponse res) {

        AuthenticationDto authenticationDto = loginService.login(loginRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getUsername(),
                        loginRequest.getPassword()
                )
        );


//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtProvider.generateJwtToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String generateToken = jwtProvider.generateToken(authentication);
        String generateRefreshToken = jwtProvider.generateRefreshToken(authentication);

        Cookie accessToken = cookieUtil.createCookies(JwtProvider.ACCESS_TOKEN_NAME, generateToken);
        Cookie refreshToken = cookieUtil.createCookies(JwtProvider.REFRESH_TOKEN_NAME, generateRefreshToken);

        //redisUtil.setDataExpire(generateRefreshToken, authentication.getName(), JwtProvider.REFRESH_TOKEN_VALIDATION_SECOND);
        //redisUtil.setDataExpire(authentication.getName(), generateRefreshToken, JwtProvider.REFRESH_TOKEN_VALIDATION_SECOND);

        res.addCookie(accessToken);
        res.addCookie(refreshToken);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("accesstoken", generateToken);
        responseHeader.set("refreshtoken", generateRefreshToken);

        return ResponseEntity.ok()
                //.header("accesstoken", generateToken)
                .headers(responseHeader)
                .body(authenticationDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignDto signUpRequest) {
        return ResponseEntity.ok().body(loginService.registerUser(signUpRequest));
    }

    @GetMapping("/sendEmailCode")
    public ResponseEntity<String> sendMailCode(@Valid @RequestBody SignDto signUpRequest) throws Exception {
        emailService.sendEmailMessage(signUpRequest.getEmail());
        return ResponseEntity.ok().body("send code");
    }
}
