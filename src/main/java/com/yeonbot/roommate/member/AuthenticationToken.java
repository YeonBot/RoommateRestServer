package com.yeonbot.roommate.member;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
// client에 리턴해줄 값을 모아놓은 클래스입니다. 접속한 사용자는 누구며,
// 권한은 이런것이 있다. 앞으로 요청할때는 token 값을 가지고 요청하라, 이런 의미
public class AuthenticationToken {

    private String memberId;
    private Collection authorities;
    private String token;

    public AuthenticationToken(String memberId, Collection collection, String token) {
        this.memberId = memberId;
        this.authorities = collection;
        this.token = token;
    }
}
