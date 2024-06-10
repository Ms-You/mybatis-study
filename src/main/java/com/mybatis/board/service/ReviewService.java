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
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public ReviewDTO.InfoRes updateReview(Long reviewId,
                                          ReviewDTO.ReviewReq reviewReq,
                                          MultipartFile[] reviewImageFiles,
                                          MultipartFile receiptImage) {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(
                () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
        );

        ReviewVO reviewVO = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(ErrorCode.REVIEW_NOT_FOUND)
        );

        validateMemberWriteReview(memberVO, reviewVO);

        if(reviewReq != null) {
            reviewVO.update(reviewReq.getTitle(), reviewReq.getContent(), reviewReq.getStoreName(), reviewReq.getRegion());

             reviewRepository.update(reviewVO);
        }

        handleReviewImages(reviewVO, reviewImageFiles);
        handleReceiptImage(reviewVO, receiptImage);

        ReviewVO updatedReviewVO = reviewRepository.findById(reviewVO.getReviewId()).orElseThrow(
                () -> new GlobalException(ErrorCode.REVIEW_NOT_FOUND)
        );

        return new ReviewDTO.InfoRes().toResByReviewVO(updatedReviewVO);
    }

    private void handleReviewImages(ReviewVO reviewVO, MultipartFile[] reviewImageFiles) {
        if(reviewImageFiles != null) {
            reviewVO.getReviewImageVOList().forEach(reviewImageVO -> fileUploadUtil.deleteFile(reviewImageVO.getName()));
            reviewVO.getReviewImageVOList().clear();

             reviewImageRepository.deleteAllByReviewId(reviewVO.getReviewId());

            Arrays.stream(reviewImageFiles).forEach((reviewImageFile) -> {
                if (!reviewImageFile.isEmpty()) {
                    Path filePath = fileUploadUtil.store(reviewImageFile);

                    ReviewImageVO reviewImageVO = ReviewImageVO.builder()
                            .name(filePath.getFileName().toString())
                            .url("http://localhost:8080/review/img/" + filePath.getFileName().toString())
                            .reviewId(reviewVO.getReviewId())
                            .build();

                    reviewImageRepository.save(reviewImageVO);
                }
            });
        }
    }

    private void handleReceiptImage(ReviewVO reviewVO, MultipartFile receiptImage) {
        if(receiptImage != null) {
            if(reviewVO.getReceiptImageVO() != null) {
                fileUploadUtil.deleteFile(reviewVO.getReceiptImageVO().getName());

                 receiptImageRepository.deleteByReviewId(reviewVO.getReviewId());
            }

            Path filePath = fileUploadUtil.store(receiptImage);
            String filename = filePath.getFileName().toString();
            String url = "http://localhost:8080/review/img/" + filePath.getFileName();

            ReceiptImageVO receiptImageVO = ReceiptImageVO.builder()
                        .name(filename)
                        .url(url)
                        .reviewId(reviewVO.getReviewId())
                        .build();

            receiptImageRepository.save(receiptImageVO);
        }
    }

    private void validateMemberWriteReview(MemberVO memberVO, ReviewVO reviewVO) {
        if(memberVO.getMemberId() != reviewVO.getMemberId()) {
            throw new GlobalException(ErrorCode.WRITER_NOT_MATCH);
        }
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(
                () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
        );

        ReviewVO reviewVO = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(ErrorCode.REVIEW_NOT_FOUND)
        );

        validateMemberWriteReview(memberVO, reviewVO);

        reviewVO.getReviewImageVOList().forEach(reviewImageVO -> fileUploadUtil.deleteFile(reviewImageVO.getName()));

        if(reviewVO.getReceiptImageVO() != null) {
            fileUploadUtil.deleteFile(reviewVO.getReceiptImageVO().getName());
        }

        reviewRepository.deleteById(reviewVO.getReviewId());
    }

    @Transactional(readOnly = true)
    public ReviewDTO.InfoRes getReviewById(Long reviewId) {
        ReviewVO reviewVO = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(ErrorCode.REVIEW_NOT_FOUND)
        );

        return new ReviewDTO.InfoRes().toResByReviewVO(reviewVO);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO.InfoRes> getReviewList() {
        List<ReviewVO> reviewVOList = reviewRepository.findAll();

        List<ReviewDTO.InfoRes> infoResList = reviewVOList.stream()
                .map(reviewVO -> {
                    ReviewDTO.InfoRes res = new ReviewDTO.InfoRes().toResByReviewVO(reviewVO);

                    return res;
                }).collect(Collectors.toList());

        return infoResList;
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO.InfoRes> searchReviewByKeyword(String keyword) {
        List<ReviewVO> reviewVOList = reviewRepository.findAllBySearch(keyword);

        List<ReviewDTO.InfoRes> infoResList = reviewVOList.stream()
                .map(reviewVO -> {
                    ReviewDTO.InfoRes res = new ReviewDTO.InfoRes().toResByReviewVO(reviewVO);

                    return res;
                }).collect(Collectors.toList());

        return infoResList;
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO.InfoRes> getMyReviews() {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(
                () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
        );

        List<ReviewVO> reviewVOList = reviewRepository.findByMemberId(memberVO.getMemberId());

        return reviewVOList.stream()
                .map(reviewVO -> {
                    ReviewDTO.InfoRes res = new ReviewDTO.InfoRes().toResByReviewVO(reviewVO);

                    return res;
                }).collect(Collectors.toList());
    }

}
