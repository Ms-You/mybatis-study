package com.mybatis.board.repository;

import com.mybatis.board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final SqlSessionTemplate sqlSessionTemplate;

    public void save(MemberVO memberVO) {
        sqlSessionTemplate.insert("Member.saveMember", memberVO);
    }

    public void update(MemberVO memberVO) {
        sqlSessionTemplate.update("Member.updateMember", memberVO);
    }

    public Optional<MemberVO> findById(Long id) {
        return Optional.ofNullable(sqlSessionTemplate.selectOne("Member.selectMemberById", id));
    }

    public Optional<MemberVO> findByLoginId(String loginId) {
        return Optional.ofNullable(sqlSessionTemplate.selectOne("Member.findByLoginId", loginId));
    }

}
