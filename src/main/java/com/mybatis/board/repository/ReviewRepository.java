package com.mybatis.board.repository;

import com.mybatis.board.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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
}
