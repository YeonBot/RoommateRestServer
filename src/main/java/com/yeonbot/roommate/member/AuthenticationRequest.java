package com.yeonbot.roommate.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//단순히 ID와 PW를 받아오는 객체입니다. client에서 인증에 요청하는 값을 저장하는 클래스입니다.
public class AuthenticationRequest {
    private String memberId;
    private String password;

}
