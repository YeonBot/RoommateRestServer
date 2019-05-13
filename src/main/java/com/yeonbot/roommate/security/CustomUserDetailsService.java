package com.yeonbot.roommate.security;

import com.yeonbot.roommate.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return
                Optional.ofNullable(memberRepository.findByMemberId(memberId))
                        .filter(m -> m!= null)
                        .map(m -> new SecurityMember(m)).get();
    }
}