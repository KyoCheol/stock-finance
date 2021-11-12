package com.board.seochu.finance.api.login.dto;

import com.board.seochu.finance.api.member.domain.entity.Members;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "'email' is a required input value")
    @Email(message = "It is not in email format")
    private String email;

    @NotBlank(message = "'password' is a required input value")
    private String password;

    public Members toEntity() {
        Members build = Members.builder()
                .email(email)
                .password(password)
                .build();

        return build;
    }
}
