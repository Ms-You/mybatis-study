package com.mybatis.board.repository;

import com.mybatis.board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final SqlSessionTemplate sqlSessionTemplate;

    public void save(MemberVO memberVO) {
        sqlSessionTemplate.insert("Member.save", memberVO);
    }


}
