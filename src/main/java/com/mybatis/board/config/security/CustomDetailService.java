package com.mybatis.board.config.security;

import com.mybatis.board.repository.MemberRepository;
import com.mybatis.board.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomDetailService implements UserDetailsService {
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(MemberVO memberVO) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(memberVO.getRoles().toString());

        return new User(memberVO.getLoginId(), memberVO.getPassword(), Collections.singleton(grantedAuthority));
    }
}
