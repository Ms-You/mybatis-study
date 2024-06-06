package com.mybatis.board.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReceiptImageVO {
    private Long receiptImageId;
    private String name;
    private String url;
    private Long reviewId;

    @Builder
    private ReceiptImageVO(String name, String url, Long reviewId) {
        this.url = url;
        this.name = name;
        this.reviewId = reviewId;
    }
}
