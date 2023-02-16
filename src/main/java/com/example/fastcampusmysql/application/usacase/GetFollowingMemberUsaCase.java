package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GetFollowingMemberUsaCase {

    private final MemberReadService memberReadService;

    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        /**
         * 1. fromMemberId = memberId -> FollowList
         * 2. 1번을 순회하면서 회원 정보를 찾으면 된다.
         */
        List<Long> followings = followReadService.getFollowings(memberId)
                .stream().map(Follow::getToMemberId).toList();

        return memberReadService.getMembers(followings);
    }

}
