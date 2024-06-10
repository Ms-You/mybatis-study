package com.mybatis.board.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewImageVO {
    private Long reviewImageId;
    private String url;
    private String name;
    private Long reviewId;

    @Builder
    private ReviewImageVO(String url, String name, Long reviewId) {
        this.url = url;
        this.name = name;
        this.reviewId = reviewId;
    }
}
