package com.mybatis.board.dto;

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
}
