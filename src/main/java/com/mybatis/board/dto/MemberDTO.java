package com.mybatis.board.dto;

import com.mybatis.board.vo.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDTO {
    @Getter
    @NoArgsConstructor
    public static class JoinReq {
        private String loginId;
        private String password;
        private String passwordConfirm;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginReq {
        private String loginId;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InfoRes {
        private Long id;
        private String nickname;
        private String image;

        public InfoRes convertByMemberVO(MemberVO memberVO) {
            this.id = memberVO.getMemberId();
            this.nickname = memberVO.getNickname();
            this.image = memberVO.getMemberImageVO() != null ? memberVO.getMemberImageVO().getUrl() : "";

            return this;
        }
    }
}
