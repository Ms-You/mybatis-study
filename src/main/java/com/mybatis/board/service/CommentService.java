package com.mybatis.board.service;

import com.mybatis.board.common.ErrorCode;
import com.mybatis.board.common.GlobalException;
import com.mybatis.board.config.jwt.SecurityUtil;
import com.mybatis.board.dto.CommentDTO;
import com.mybatis.board.repository.CommentRepository;
import com.mybatis.board.repository.MemberRepository;
import com.mybatis.board.repository.ReviewRepository;
import com.mybatis.board.vo.CommentVO;
import com.mybatis.board.vo.MemberVO;
import com.mybatis.board.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentDTO.InfoRes writeComment(Long reviewId, CommentDTO.CommentReq commentReq) {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(
                () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
        );

        ReviewVO reviewVO = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(ErrorCode.REVIEW_NOT_FOUND)
        );

        CommentVO commentVO = CommentVO.builder()
                .content(commentReq.getContent())
                .reviewId(reviewVO.getReviewId())
                .memberId(memberVO.getMemberId())
                .build();

        commentRepository.save(commentVO);

        CommentVO savedCommentVo = commentRepository.findById(commentVO.getCommentId()).orElseThrow(
                () -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND)
        );

        return CommentDTO.InfoRes.builder()
                .id(savedCommentVo.getCommentId())
                .content(savedCommentVo.getContent())
                .memberId(memberVO.getMemberId())
                .nickname(memberVO.getNickname())
                .image(memberVO.getMemberImageVO() != null ? memberVO.getMemberImageVO().getUrl() : "")
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentDTO.InfoRes> getComments(Long reviewId) {
        return commentRepository.findAllCommentsWithMemberInfoByReviewId(reviewId);
//        List<CommentVO> commentVOList = commentRepository.findAllByReviewId(reviewId);
//
//        return commentVOList.stream()
//                .map(commentVO -> {
//                    MemberVO memberVO = memberRepository.findById(commentVO.getMemberId()).orElseThrow(
//                            () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
//                    );
//
//                    CommentDTO.InfoRes res = CommentDTO.InfoRes.builder()
//                            .id(commentVO.getCommentId())
//                            .content(commentVO.getContent())
//                            .memberId(memberVO.getMemberId())
//                            .nickname(memberVO.getNickname())
//                            .image(memberVO.getMemberImageVO() != null ? memberVO.getMemberImageVO().getUrl() : "")
//                            .build();
//
//                    return res;
//                }).collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO.InfoRes updateComment(Long commentId, CommentDTO.CommentReq commentReq) {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(
                () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
        );

        CommentVO commentVO = commentRepository.findById(commentId).orElseThrow(
                () -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND)
        );

        validateMemberWroteComment(memberVO, commentVO);

        commentVO.updateContent(commentReq.getContent());
        commentRepository.save(commentVO);

        CommentVO savedCommentVo = commentRepository.findById(commentVO.getCommentId()).orElseThrow(
                () -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND)
        );

        return CommentDTO.InfoRes.builder()
                .id(savedCommentVo.getCommentId())
                .content(savedCommentVo.getContent())
                .memberId(memberVO.getMemberId())
                .nickname(memberVO.getNickname())
                .image(memberVO.getMemberImageVO() != null ? memberVO.getMemberImageVO().getUrl() : "")
                .build();
    }

    private void validateMemberWroteComment(MemberVO memberVO, CommentVO commentVO) {
        if(commentVO.getMemberId() != memberVO.getMemberId()) {
            throw new GlobalException(ErrorCode.WRITER_NOT_MATCH);
        }
    }

    @Transactional
    public void deleteComment(Long commentId) {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(
                () -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)
        );

        CommentVO commentVO = commentRepository.findById(commentId).orElseThrow(
                () -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND)
        );

        validateMemberWroteComment(memberVO, commentVO);

        commentRepository.deleteById(commentVO.getCommentId());
    }
}
