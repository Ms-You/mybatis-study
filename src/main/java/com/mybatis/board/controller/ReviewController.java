package com.mybatis.board.controller;

import com.mybatis.board.dto.ReviewDTO;
import com.mybatis.board.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity writeReview(@RequestPart(value = "reviewReq") ReviewDTO.ReviewReq reviewReq,
                                      @RequestPart(value = "images", required = false)MultipartFile[] reviewImageFiles,
                                      @RequestPart(value = "receiptImage", required = false) MultipartFile receiptFile) {
        ReviewDTO.InfoRes res = reviewService.writeReview(reviewReq, reviewImageFiles, receiptFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity updateReview(@PathVariable(value = "reviewId") Long reviewId,
                                       @RequestPart(value = "reviewReq", required = false) ReviewDTO.ReviewReq reviewReq,
                                       @RequestPart(value = "images", required = false) MultipartFile[] reviewImageFiles,
                                       @RequestPart(value = "receiptImage", required = false) MultipartFile receiptFile) {
        ReviewDTO.InfoRes res = reviewService.updateReview(reviewId, reviewReq, reviewImageFiles, receiptFile);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable(value = "reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity getReview(@PathVariable(value = "reviewId") Long reviewId) {
        ReviewDTO.InfoRes res = reviewService.getReviewById(reviewId);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/reviews")
    public ResponseEntity getReviews() {
        List<ReviewDTO.InfoRes> resList = reviewService.getReviewList();

        return ResponseEntity.ok().body(resList);
    }

    @GetMapping("/review")
    public ResponseEntity searchReview(@RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        List<ReviewDTO.InfoRes> resList = reviewService.searchReviewByKeyword(keyword);

        return ResponseEntity.ok().body(resList);
    }

    @GetMapping("/reviews/user/my")
    public ResponseEntity getMyReviews() {
        List<ReviewDTO.InfoRes> resList = reviewService.getMyReviews();

        return ResponseEntity.ok().body(resList);
    }

}
