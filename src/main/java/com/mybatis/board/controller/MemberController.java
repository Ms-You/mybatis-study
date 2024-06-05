package com.mybatis.board.controller;

import com.mybatis.board.dto.MemberDTO;
import com.mybatis.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody MemberDTO.JoinReq joinReq) {
        memberService.create(joinReq);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
