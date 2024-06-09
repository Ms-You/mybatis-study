package com.mybatis.board.repository;

import com.mybatis.board.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReviewRepository {
    private final SqlSessionTemplate sqlSessionTemplate;

    public void save(ReviewVO reviewVO) {
        sqlSessionTemplate.insert("Review.saveReview", reviewVO);
    }

    public Optional<ReviewVO> findById(Long reviewId) {
        return Optional.ofNullable(sqlSessionTemplate.selectOne("Review.selectReviewById", reviewId));
    }

    public List<ReviewVO> findAll() {
        return sqlSessionTemplate.selectList("Review.selectReviews");
    }

    public List<ReviewVO> findAllBySearch(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        return sqlSessionTemplate.selectList("Review.selectReviewsWithKeyword", searchKeyword);
    }

    public List<ReviewVO> findByMemberId(Long memberId) {
        return sqlSessionTemplate.selectList("Review.selectReviewByMemberId", memberId);
    }

    public void deleteById(Long reviewId) {
        sqlSessionTemplate.delete("ReviewImage.deleteReviewImagesByReviewId", reviewId);
        sqlSessionTemplate.delete("ReceiptImage.deleteReceiptImageByReviewId", reviewId);
        sqlSessionTemplate.delete("Comment.deleteCommentsByReviewId", reviewId);
        sqlSessionTemplate.delete("Review.deleteById", reviewId);
    }
}
