package com.mybatis.board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private RoleType roles;

    @Builder
    private MemberVO(String loginId, String password, String nickname, RoleType roles) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
    }
}
