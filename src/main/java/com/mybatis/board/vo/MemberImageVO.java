package com.mybatis.board.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberImageVO {
    private Long memberImageId;
    private String url;
    private String name;
    private Long memberId;

    @Builder
    private MemberImageVO(String url, String name, Long memberId) {
        this.url = url;
        this.name = name;
        this.memberId = memberId;
    }

    public void updateMemberImage(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
