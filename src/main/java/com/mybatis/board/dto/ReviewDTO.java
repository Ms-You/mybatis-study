package com.mybatis.board.dto;

import com.mybatis.board.vo.ReviewVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewDTO {
    @Getter
    @NoArgsConstructor
    public static class ReviewReq {
        private String title;
        private String content;
        private String storeName;
        private String region;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InfoRes {
        private Long id;
        private String title;
        private String content;
        private List<ReviewImageDTO.InfoRes> images;
        private String receiptImage;
        private String storeName;
        private String region;
        private Long memberId;

        public InfoRes toResByReviewVO(ReviewVO reviewVO) {
            this.images = reviewVO.getReviewImageVOList().stream()
                    .map(reviewImageVO -> ReviewImageDTO.InfoRes.builder()
                            .id(reviewImageVO.getReviewImageId())
                            .url(reviewImageVO.getUrl())
                            .name(reviewImageVO.getName())
                            .build())
                    .collect(Collectors.toList());

            this.id = reviewVO.getReviewId();
            this.title = reviewVO.getTitle();
            this.content = reviewVO.getContent();
            this.region = reviewVO.getRegion();
            this.storeName = reviewVO.getStoreName();
            this.receiptImage = reviewVO.getReceiptImageVO() != null ? reviewVO.getReceiptImageVO().getUrl() : "";
            this.memberId = reviewVO.getMemberId();

            return this;
        }
    }
}
