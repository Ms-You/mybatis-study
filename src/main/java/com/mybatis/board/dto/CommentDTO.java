package com.mybatis.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDTO {

    @Getter
    @NoArgsConstructor
    public static class CommentReq {
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InfoRes {
        private Long id;
        private String content;
        private Long memberId;
        private String nickname;
        private String image;
    }
}
