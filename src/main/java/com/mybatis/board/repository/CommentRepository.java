package com.mybatis.board.repository;

import com.mybatis.board.dto.CommentDTO;
import com.mybatis.board.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentRepository {
    private final SqlSessionTemplate sqlSessionTemplate;

    public void save(CommentVO commentVO) {
        sqlSessionTemplate.insert("Comment.saveComment", commentVO);
    }

    public Optional<CommentVO> findById(Long commentId) {
        return Optional.ofNullable(sqlSessionTemplate.selectOne("Comment.selectCommentById", commentId));
    }

    public List<CommentDTO.InfoRes> findAllCommentsWithMemberInfoByReviewId(Long reviewId) {
        return sqlSessionTemplate.selectList("CommentDTO.findAllCommentsWithMemberInfoByReviewId", reviewId);
    }

    public void deleteById(Long commentId) {
        sqlSessionTemplate.delete("Comment.deleteCommentById", commentId);
    }
}
