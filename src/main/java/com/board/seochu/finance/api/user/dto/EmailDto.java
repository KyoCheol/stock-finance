package com.board.seochu.finance.api.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail.properties.mail.smtp")
@Configuration
public class EmailDto {
    private String name;
    private String link;
    private Long validTime;
}
