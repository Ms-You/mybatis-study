package com.mybatis.board.service;

import com.mybatis.board.common.ErrorCode;
import com.mybatis.board.common.GlobalException;
import com.mybatis.board.config.file.FileUploadUtil;
import com.mybatis.board.config.jwt.SecurityUtil;
import com.mybatis.board.dto.ReviewDTO;
import com.mybatis.board.repository.MemberRepository;
import com.mybatis.board.repository.ReceiptImageRepository;
import com.mybatis.board.repository.ReviewImageRepository;
import com.mybatis.board.repository.ReviewRepository;
import com.mybatis.board.vo.MemberVO;
import com.mybatis.board.vo.ReceiptImageVO;
import com.mybatis.board.vo.ReviewImageVO;
import com.mybatis.board.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final MemberRepository memberRepository;
    private final FileUploadUtil fileUploadUtil;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReceiptImageRepository receiptImageRepository;

    @Transactional
    public ReviewDTO.InfoRes writeReview(ReviewDTO.ReviewReq reviewReq,
                                         MultipartFile[] reviewImageFiles,
                                         MultipartFile receiptFile) {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(
                () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
        );

        ReviewVO reviewVO = ReviewVO.builder()
                .title(reviewReq.getTitle())
                .content(reviewReq.getContent())
                .region(reviewReq.getRegion())
                .storeName(reviewReq.getStoreName())
                .memberId(memberVO.getMemberId())
                .build();

        reviewRepository.save(reviewVO);

        if(reviewImageFiles != null) {
            Arrays.stream(reviewImageFiles).forEach((reviewImageFile) -> {
                if(!reviewImageFile.isEmpty()) {
                    Path filePath = fileUploadUtil.store(reviewImageFile);

                    ReviewImageVO reviewImageVO = ReviewImageVO.builder()
                            .name(filePath.getFileName().toString())
                            .url("http://localhost:8080/review/img/" +filePath.getFileName().toString())
                            .reviewId(reviewVO.getReviewId())
                            .build();

                    reviewImageRepository.save(reviewImageVO);
                }
            });
        }

        if(receiptFile != null && !receiptFile.isEmpty()) {
            Path filePath = fileUploadUtil.store(receiptFile);

            ReceiptImageVO receiptImageVO = ReceiptImageVO.builder()
                    .name(filePath.getFileName().toString())
                    .url("http://localhost:8080/review/img/" +filePath.getFileName().toString())
                    .reviewId(reviewVO.getReviewId())
                    .build();

            receiptImageRepository.save(receiptImageVO);
        }

        ReviewVO savedReviewVO = reviewRepository.findById(reviewVO.getReviewId()).orElseThrow(
                () -> new GlobalException(ErrorCode.REVIEW_NOT_FOUND)
        );

        return new ReviewDTO.InfoRes().toResByReviewVO(savedReviewVO);
    }
}
