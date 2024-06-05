package com.mybatis.board.service;

import com.mybatis.board.dto.MemberDTO;
import com.mybatis.board.repository.MemberRepository;
import com.mybatis.board.vo.MemberVO;
import com.mybatis.board.vo.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void create(MemberDTO.JoinReq joinReq) {
        MemberVO memberVO = MemberVO.builder()
                .loginId(joinReq.getLoginId())
                .password(joinReq.getPassword())
                .nickname(joinReq.getNickname())
                .roles(RoleType.ROLE_USER)
                .build();

        memberRepository.save(memberVO);
    }

}
