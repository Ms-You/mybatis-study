package com.mybatis.board.service;

import com.mybatis.board.common.ErrorCode;
import com.mybatis.board.common.GlobalException;
import com.mybatis.board.config.jwt.SecurityUtil;
import com.mybatis.board.config.jwt.TokenProvider;
import com.mybatis.board.dto.MemberDTO;
import com.mybatis.board.repository.MemberRepository;
import com.mybatis.board.vo.MemberVO;
import com.mybatis.board.vo.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public void create(MemberDTO.JoinReq joinReq) {
        memberRepository.findByLoginId(joinReq.getLoginId()).ifPresent((a) -> {
            throw new GlobalException(ErrorCode.MEMBER_ALREADY_EXISTS);
        });

        checkPassword(joinReq.getPassword(), joinReq.getPasswordConfirm());

        MemberVO memberVO = MemberVO.builder()
                .loginId(joinReq.getLoginId())
                .password(passwordEncoder.encode(joinReq.getPassword()))
                .nickname(joinReq.getNickname())
                .roles(RoleType.ROLE_USER)
                .build();

        memberRepository.save(memberVO);
    }

    private void checkPassword(String password, String passwordConfirm) {
        if(!password.equals(passwordConfirm)) {
            throw new GlobalException(ErrorCode.PASSWORD_NOT_MATCHED);
        }
    }

    @Transactional
    public String login(MemberDTO.LoginReq loginReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getLoginId(), loginReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = tokenProvider.generateToken(authentication);

        return accessToken;
    }

    @Transactional(readOnly = true)
    public MemberDTO.InfoRes getMemberInfo() {
        MemberVO memberVO = memberRepository.findByLoginId(SecurityUtil.getCurrentMember()).orElseThrow(() -> {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        });

        return new MemberDTO.InfoRes().convertByMemberVO(memberVO);
    }

}
