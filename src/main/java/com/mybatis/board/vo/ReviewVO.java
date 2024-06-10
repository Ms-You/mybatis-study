package com.mybatis.board.vo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewVO {
    private Long reviewId;
    private String title;
    private String content;
    private String storeName;
    private String region;
    private Long memberId;
    private List<ReviewImageVO> reviewImageVOList = new ArrayList<>();
    private List<CommentVO> commentVOList = new ArrayList<>();
    private ReceiptImageVO receiptImageVO;

    @Builder
    private ReviewVO(String title, String content, String storeName, String region, Long memberId) {
        this.title = title;
        this.content = content;
        this.storeName = storeName;
        this.region = region;
        this.memberId = memberId;
    }

    public void update(String title, String content, String storeName, String region) {
        this.title = title;
        this.content = content;
        this.storeName = storeName;
        this.region = region;
    }
}
