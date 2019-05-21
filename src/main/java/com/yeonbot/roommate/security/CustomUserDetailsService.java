package com.yeonbot.roommate.security;

import com.yeonbot.roommate.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//UserDetailsService 는 DB에서 user정보를 가져오는 역할을 한다.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    //Database에 접근해서 사용자 정보를 가져오는 역할을 한다.
    //parameter로 받은 memberID 가 DB에 있는지 JPA Repository 로 확인
    //null check 후
    //SecurityMember 변수를 이용해 security 에서 사용자의 정보를 담는 객체 UserDetails를 반환
    //userDetails 는 VO 역할을 한다고 보면 된다.
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return
                Optional.ofNullable(memberRepository.findByMemberId(memberId))
                        .filter(m -> m!= null)
                        .map(m -> new SecurityMember(m)).get();
    }
}