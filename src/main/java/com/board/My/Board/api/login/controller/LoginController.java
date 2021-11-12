package com.board.seochu.finance.api.login.controller;

import com.board.seochu.finance.api.login.dto.AuthenticationDto;
import com.board.seochu.finance.api.login.dto.LoginDto;
import com.board.seochu.finance.api.login.service.LoginService;
import com.board.seochu.finance.api.register.dto.RegisterDto;
import com.board.seochu.finance.api.register.service.RegisterService;
import com.board.seochu.finance.util.auth.AuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final LoginService loginService;
    private final AuthProvider authProvider;
    private final RegisterService registerService;

    /**
     * method 설명 : 회원가입
     * param joinDto
     * throws Exception
     */
    @PostMapping(value = {"/api/register"})
    public ResponseEntity<Boolean> register(@Valid @RequestBody RegisterDto registerDto) throws  Exception {
        return ResponseEntity.ok().body(registerService.regMember(registerDto));
    }

    /**
     * method 설명 : 로그인
     * param loginDto
     * throws Exception
     */
    @PostMapping(value = {"/signin"})
    public ResponseEntity<AuthenticationDto> login(@Valid @RequestBody LoginDto loginDto) throws Exception {

        log.info(" S I G N I N =====================>> ");
        AuthenticationDto authentication = loginService.loginMember(loginDto);

        return ResponseEntity.ok()
                .header("accesstoken", authProvider
                        .createToken(
                                authentication.getId(),
                                authentication.getEmail(),
                                "USER"))
                .body(authentication);
    }

}
