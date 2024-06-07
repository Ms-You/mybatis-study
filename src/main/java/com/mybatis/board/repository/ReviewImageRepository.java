package com.mybatis.board.repository;

import com.mybatis.board.vo.ReviewImageVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewImageRepository {
    private final SqlSessionTemplate sqlSessionTemplate;

    public void save(ReviewImageVO reviewImageVO) {
        sqlSessionTemplate.insert("ReviewImage.saveReviewImage", reviewImageVO);
    }
}
