package com.mybatis.board.controller;

import com.mybatis.board.dto.CommentDTO;
import com.mybatis.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/review/{reviewId}/comment")
    public ResponseEntity writeReview(@PathVariable(value = "reviewId") Long reviewId,
                                      @RequestBody CommentDTO.CommentReq commentReq) {
        CommentDTO.InfoRes res = commentService.writeComment(reviewId, commentReq);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/review/{reviewId}/comment")
    public ResponseEntity getComments(@PathVariable(value = "reviewId") Long reviewId) {
        List<CommentDTO.InfoRes> resList = commentService.getComments(reviewId);

        return ResponseEntity.ok().body(resList);
    }

    @PutMapping("/review/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable(value = "commentId") Long commentId,
                                        @RequestBody CommentDTO.CommentReq commentReq) {
        CommentDTO.InfoRes res = commentService.updateComment(commentId, commentReq);

        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/review/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(value = "commentId") Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}
