package com.board.seochu.finance.api.login.controller;

import com.board.seochu.finance.api.login.dto.AuthenticationDTO;
import com.board.seochu.finance.api.login.dto.LoginDTO;
import com.board.seochu.finance.api.login.dto.SignDTO;
import com.board.seochu.finance.api.login.service.LoginService;
import com.board.seochu.finance.api.role.domain.entity.Role;
import com.board.seochu.finance.api.role.domain.entity.RoleName;
import com.board.seochu.finance.api.role.domain.repository.RoleRepository;
import com.board.seochu.finance.api.user.domain.entity.User;
import com.board.seochu.finance.api.user.domain.repository.UserRepository;
import com.board.seochu.finance.util.auth.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationDTO> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {

        AuthenticationDTO authenticationDto = loginService.login(loginRequest);

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
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
//        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
//                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail());

        Set<String> strRoles = signUpRequest.getRole();
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

        //user.setRoles(roles);
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }
}
