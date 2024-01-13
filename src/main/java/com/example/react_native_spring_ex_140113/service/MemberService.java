package com.example.react_native_spring_ex_140113.service;

import com.example.react_native_spring_ex_140113.dto.MemberDto;
import com.example.react_native_spring_ex_140113.entity.Member;
import com.example.react_native_spring_ex_140113.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 전체 조회
    public List<MemberDto> getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();
        for(Member member : members) {
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }
    // 회원 상세 조회
    public MemberDto getMemberDetail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
        );
        return convertEntityToDto(member);
    }
    // 회원 수정
    public boolean modifyMember(MemberDto memberDto) {
        try {
            Member member = memberRepository.findByEmail(memberDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            member.setName(memberDto.getName());
            member.setImage(memberDto.getImage());
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during modifyMember: {}", e.getMessage(), e);
            return false;
        }
    }
    // 회원 삭제
    public boolean deleteMember(String email) {
        try {
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            memberRepository.delete(member);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during deleteMember: {}", e.getMessage(), e);
            return false;
        }
    }

    // 회원 엔티티를 회원 DTO로 변환
    private MemberDto convertEntityToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        memberDto.setPwd(member.getPwd());
        memberDto.setImage(member.getImage());
        memberDto.setRegDate(member.getRegDate());
        return memberDto;
    }
}
