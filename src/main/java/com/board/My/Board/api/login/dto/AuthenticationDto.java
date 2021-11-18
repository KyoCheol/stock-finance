package com.board.seochu.finance.api.login.dto;

import com.board.seochu.finance.api.role.domain.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationDto {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();

}
