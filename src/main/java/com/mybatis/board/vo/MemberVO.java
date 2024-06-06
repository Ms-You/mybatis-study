package com.mybatis.board.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberVO {
    private Long memberId;
    private String loginId;
    private String password;
    private String nickname;
    private RoleType roles;
    private MemberImageVO memberImageVO;

    @Builder
    private MemberVO(String loginId, String password, String nickname, RoleType roles) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
    }
}
