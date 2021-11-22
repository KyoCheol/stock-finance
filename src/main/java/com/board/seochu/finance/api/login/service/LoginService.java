package com.board.seochu.finance.api.login.service;

import com.board.seochu.finance.api.login.dto.AuthenticationDto;
import com.board.seochu.finance.api.login.dto.LoginDTO;
import com.board.seochu.finance.api.login.dto.SignDTO;
import com.board.seochu.finance.api.role.domain.entity.Role;
import com.board.seochu.finance.api.role.domain.entity.RoleName;
import com.board.seochu.finance.api.role.domain.repository.RoleRepository;
import com.board.seochu.finance.api.user.domain.entity.User;
import com.board.seochu.finance.api.user.domain.repository.UserRepository;
import com.board.seochu.finance.exception.DuplicatedException;
import com.board.seochu.finance.exception.ForbiddenException;
import com.board.seochu.finance.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service("LoginService")
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    private final ModelMapper modelMapper;

    public AuthenticationDto login(LoginDTO loginDto) {

        // dto ---> entity
        User loginEntity = loginDto.toEntity();

        // 회원 엔티티 객체 생성 및 조회시작
        User member = userRepository.findByEmail(loginEntity.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if(!passwordEncoder.matches(loginEntity.getPassword(), member.getPassword()))
            throw new ForbiddenException("Passwords do not match");

        AuthenticationDto authenticationDto = modelMapper.map(member, AuthenticationDto.class);
        return authenticationDto;
    }

    public User registerUser(SignDTO signDTO) {

        if (userRepository.existsByUsername(signDTO.getUsername()))
            throw new DuplicatedException("아이디 중복");

        if (userRepository.existsByEmail(signDTO.getEmail()))
            throw new DuplicatedException("이메일 중복");

        User user = new User(signDTO.getName(), signDTO.getUsername(),
                signDTO.getEmail());

        Set<String> strRoles = signDTO.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setPassword(encoder.encode(signDTO.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);

        return user;
    }
}

