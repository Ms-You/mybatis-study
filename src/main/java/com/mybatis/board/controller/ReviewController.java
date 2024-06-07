package com.mybatis.board.controller;

import com.mybatis.board.dto.ReviewDTO;
import com.mybatis.board.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
