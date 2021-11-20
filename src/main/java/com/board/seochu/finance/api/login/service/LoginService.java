package com.board.seochu.finance.api.login.service;

import com.board.seochu.finance.api.login.dto.AuthenticationDTO;
import com.board.seochu.finance.api.login.dto.LoginDTO;
import com.board.seochu.finance.api.user.domain.entity.User;
import com.board.seochu.finance.api.user.domain.repository.UserRepository;
import com.board.seochu.finance.exception.ForbiddenException;
import com.board.seochu.finance.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("loginService")
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final ModelMapper modelMapper;

    public AuthenticationDTO login(LoginDTO loginDto) {

        // dto ---> entity
        User loginEntity = loginDto.toEntity();

        // 회원 엔티티 객체 생성 및 조회시작
        User member = userRepository.findByEmail(loginEntity.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if(!passwordEncoder.matches(loginEntity.getPassword(), member.getPassword()))
            throw new ForbiddenException("Passwords do not match");

        AuthenticationDTO authenticationDto = modelMapper.map(member, AuthenticationDTO.class);

        return authenticationDto;
    }
}

