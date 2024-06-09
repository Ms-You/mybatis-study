package com.mybatis.board.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentVO {
    private Long commentId;
    private String content;
    private Long memberId;
    private Long reviewId;

    @Builder
    private CommentVO(String content, Long memberId, Long reviewId) {
        this.content = content;
        this.memberId = memberId;
        this.reviewId = reviewId;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
