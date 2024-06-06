package com.mybatis.board.vo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private List<ReviewVO> reviewVOList = new ArrayList<>();
    private List<CommentVO> commentVOList = new ArrayList<>();

    @Builder
    private MemberVO(String loginId, String password, String nickname, RoleType roles) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
    }
}
