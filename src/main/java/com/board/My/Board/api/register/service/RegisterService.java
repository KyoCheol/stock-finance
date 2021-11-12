package com.board.seochu.finance.api.register.service;

import com.board.seochu.finance.api.member.domain.repository.MemberRepository;
import com.board.seochu.finance.api.register.dto.RegisterDto;
import com.board.seochu.finance.exception.custom.DuplicatedException;
import com.board.seochu.finance.util.validation.Empty;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("registerService")
@RequiredArgsConstructor
public class RegisterService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final ModelMapper modelMapper;

    public boolean regMember(RegisterDto registerDto) {

        // 아이디 중복 체크
        if (!Empty.validation(memberRepository.countByEmail(registerDto.getEmail())))
            throw new DuplicatedException("Duplication Id");

        if (!Empty.validation(memberRepository.countByMobile(registerDto.getMobile())))
            throw new DuplicatedException("Duplication Mobile");

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        memberRepository.save(registerDto.toEntity());

        return true;
    }
}
