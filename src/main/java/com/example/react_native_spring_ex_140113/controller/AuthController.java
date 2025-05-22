package com.example.react_native_spring_ex_140113.controller;


import com.example.react_native_spring_ex_140113.dto.MemberDto;
import com.example.react_native_spring_ex_140113.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;

@Slf4j
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원 가입 여부 확인
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> memberExists(@PathVariable String email) {
        log.info("email: {}", email);
        boolean isTrue = authService.isMember(email);
        return ResponseEntity.ok(!isTrue);
    }

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody MemberDto memberDto) {
        log.info("memberDto: {}", memberDto.getEmail());
        log.info("memberDto: {}", memberDto.getPwd());
        boolean isTrue = authService.signup(memberDto);
        return ResponseEntity.ok(isTrue);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody MemberDto memberDto) {
        log.info("memberDto: {}", memberDto.getEmail());
        log.info("memberDto: {}", memberDto.getPwd());
        boolean isTrue = authService.login(memberDto.getEmail(), memberDto.getPwd());
        return ResponseEntity.ok(isTrue);
    }



}
