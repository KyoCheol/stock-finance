package com.board.seochu.finance.api.file.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FileDto {
    private String name;
    private String url;
    private String type;
    private Long size;

    public FileDto(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }
}
