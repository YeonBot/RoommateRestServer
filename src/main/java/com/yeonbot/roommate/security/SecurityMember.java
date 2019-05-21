package com.yeonbot.roommate.security;

import com.yeonbot.roommate.member.Member;
import com.yeonbot.roommate.member.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

//spring Security 에서 UserDetails 인터페이스를 구성 해논 user를 확장해서 사용.
public class SecurityMember extends User {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    //생성자에서 user의생성자에 username password 권한정보 collection 을 넣는다.
    public SecurityMember(Member member) {
        super(member.getMemberId(), member.getMemberPassword(), makeGrantedAuthority(member.getRoles()));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
        return list;
    }
}
