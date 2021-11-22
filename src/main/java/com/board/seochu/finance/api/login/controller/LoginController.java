package com.board.seochu.finance.api.login.controller;

import com.board.seochu.finance.api.login.dto.AuthenticationDto;
import com.board.seochu.finance.api.login.dto.LoginDTO;
import com.board.seochu.finance.api.login.dto.SignDTO;
import com.board.seochu.finance.api.login.service.LoginService;
import com.board.seochu.finance.api.role.domain.repository.RoleRepository;
import com.board.seochu.finance.api.user.domain.entity.User;
import com.board.seochu.finance.api.user.domain.repository.UserRepository;
import com.board.seochu.finance.api.user.service.EmailService;
import com.board.seochu.finance.util.auth.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

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

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationDto> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {

        AuthenticationDto authenticationDto = loginService.login(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getUsername(),
                        loginRequest.getPassword()
                )
        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok()
                .header("accesstoken", jwtProvider.generateJwtToken(authentication))
                .body(authenticationDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignDTO signUpRequest) {

        return ResponseEntity.ok().body(loginService.registerUser(signUpRequest));
    }

    @GetMapping("/sendEmailCode")
    public ResponseEntity<String> sendMailCode(@Valid @RequestBody SignDTO signUpRequest) throws Exception {
        emailService.sendEmailMessage(signUpRequest.getEmail());
        return ResponseEntity.ok().body("send code");
    }
}
