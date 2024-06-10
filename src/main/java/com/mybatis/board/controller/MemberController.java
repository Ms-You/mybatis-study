package com.mybatis.board.controller;

import com.mybatis.board.dto.MemberDTO;
import com.mybatis.board.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody MemberDTO.JoinReq joinReq) {
        memberService.create(joinReq);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberDTO.LoginReq loginReq, HttpServletResponse response) {
        String token = memberService.login(loginReq);

        response.setHeader("Authorization", "Bearer " + token);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<MemberDTO.InfoRes> getMemberInfo() {
        MemberDTO.InfoRes infoRes = memberService.getMemberInfo();

        return ResponseEntity.ok(infoRes);
    }

    @PatchMapping("/user")
    public ResponseEntity updateMemberInfo(@RequestPart(value = "nickname", required = false) String nickname,
                          @RequestPart(value = "password", required = false) String password,
                          @RequestPart(value = "passwordConfirm", required = false) String passwordConfirm,
                          @RequestPart(value = "image", required = false) MultipartFile image) {
        MemberDTO.InfoRes infoRes = memberService.updateMemberInfo(nickname, password, passwordConfirm, image);

        return ResponseEntity.ok().body(infoRes);
    }


}
