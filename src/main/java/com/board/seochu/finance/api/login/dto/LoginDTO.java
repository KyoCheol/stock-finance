package com.board.seochu.finance.api.login.dto;

import com.board.seochu.finance.api.user.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDTO {
//    @NotBlank
//    @Size(min = 3, max = 60)
//    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Email(message = "It is not in email format")
    private String email;

    public User toEntity() {
        User build = User.builder()
//                .username(username)
                .password(password)
                .email(email)
                .build();

        return build;
    }
}
