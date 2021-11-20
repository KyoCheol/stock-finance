package com.board.seochu.finance.api.news.dto;

import lombok.Data;

@Data
public class NewsVO {
    private int total;
    private int start;
    private int display;
    private Item[] imems;

    class Item {
        public String title;
        public String link;
        public String category;
        public String description;
        public String telephone;
        public String address;
        public String roadAddress;
        public String mapx;
        public String mapy;
    }
}
