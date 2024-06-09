package com.mybatis.board.repository;

import com.mybatis.board.vo.MemberImageVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberImageRepository {
    private final SqlSessionTemplate sqlSessionTemplate;

    public void save(MemberImageVO memberImageVO) {
        sqlSessionTemplate.insert("MemberImage.saveMemberImage", memberImageVO);
    }
}
