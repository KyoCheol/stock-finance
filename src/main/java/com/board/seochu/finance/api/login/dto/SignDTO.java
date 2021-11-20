package com.board.seochu.finance.api.login.dto;

import com.board.seochu.finance.api.user.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public User toEntity() {
        User build = User.builder()
                .name(name)
                .username(username)
                .email(email)
                .password(password)
                .build();

        return build;
    }
}
