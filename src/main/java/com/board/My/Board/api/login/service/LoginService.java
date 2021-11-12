package com.board.seochu.finance.api.login.service;

import com.board.seochu.finance.api.login.dto.AuthenticationDto;
import com.board.seochu.finance.api.login.dto.LoginDto;
import com.board.seochu.finance.api.member.domain.entity.Members;
import com.board.seochu.finance.api.member.domain.repository.MemberRepository;
import com.board.seochu.finance.exception.custom.ForbiddenException;
import com.board.seochu.finance.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("loginService")
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final ModelMapper modelMapper;

    public AuthenticationDto loginMember(LoginDto loginDto) {

        // dto ---> entity
        Members loginEntity = loginDto.toEntity();

        // 회원 엔티티 객체 생성 및 조회시작
        Members member = memberRepository.findByEmail(loginEntity.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if(!passwordEncoder.matches(loginEntity.getPassword(), member.getPassword()))
            throw new ForbiddenException("Passwords do not match");

        AuthenticationDto authenticationDto = modelMapper.map(member, AuthenticationDto.class);

        return authenticationDto;
    }
}

