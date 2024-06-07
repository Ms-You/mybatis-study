package com.mybatis.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewImageDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InfoRes {
        private Long id;
        private String name;
        private String url;
    }
}
