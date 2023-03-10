package com.janek.simplesns.domain.member.service;

import com.janek.simplesns.domain.member.dto.RegisterMemberCommand;
import com.janek.simplesns.domain.member.entity.Member;
import com.janek.simplesns.domain.member.entity.MemberNicknameHistory;
import com.janek.simplesns.domain.member.repository.MemberNicknameHistoryRepository;
import com.janek.simplesns.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member register(RegisterMemberCommand command) {
        /*
            목표 - 회원 정보(이메일, 닉네임, 생년월일) 등록
                - 닉네임을 10자를 넘길 수 없다.
            파라미터 - memberRegisterCommand

            val member = Member.of(memberRegisterCommand)
            memberRepository.save(member)
         */
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthDay(command.birthDay())
                .build();

        Member savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(member);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        /**
         * 1. 회원의 이름 변경
         * 2. 변경 내역을 저장
         */
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryRepository.save(history);
    }

}
