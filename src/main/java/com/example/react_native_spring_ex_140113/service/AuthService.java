package com.example.react_native_spring_ex_140113.service;

import com.example.react_native_spring_ex_140113.dto.MemberDto;
import com.example.react_native_spring_ex_140113.entity.Member;
import com.example.react_native_spring_ex_140113.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final MemberRepository memberRepository;

    // 회원 가입 여부 확인
    public boolean isMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원 가입
    public boolean signup(MemberDto memberDto) {
        try {
            Member member = convertDtoToEntity(memberDto);
            memberRepository.save(member);
            return true; // Return true if member is successfully saved
        } catch (Exception e) {
            // Handle any exceptions that may occur during signup
            log.error("Error occurred during signup: {}", e.getMessage(), e);
            return false; // Return false in case of an error
        }
    }
    // 로그인
    public boolean login(String email, String pwd) {
        Optional<Member> member = memberRepository.findByEmailAndPwd(email, pwd);
        log.info("member: {}", member);
        return member.isPresent();
    }

    // 회원 Dto -> Entity
    private Member convertDtoToEntity(MemberDto memberDto) {
        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setName(memberDto.getName());
        member.setImage(memberDto.getImage());
        member.setPwd(memberDto.getPwd());
        return member;
    }
}
