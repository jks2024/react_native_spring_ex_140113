package com.example.react_native_spring_ex_140113.controller;

import com.example.react_native_spring_ex_140113.dto.MemberDto;
import com.example.react_native_spring_ex_140113.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList() {
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }
    // 회원 상세 조회
    @GetMapping("/detail/{email}")
    public ResponseEntity<MemberDto> memberDetail(@PathVariable String email) {
        MemberDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }
    // 회원 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberDto memberDto) {
        log.info("memberDto: {}", memberDto.getEmail());
        boolean isTrue = memberService.modifyMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }
    // 회원 삭제
    @GetMapping("/delete/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        log.info("email: {}", email);
        boolean isTrue = memberService.deleteMember(email);
        return ResponseEntity.ok(isTrue);
    }

}
