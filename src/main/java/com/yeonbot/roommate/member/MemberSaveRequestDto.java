package com.yeonbot.roommate.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
//Controller에서 @RequestBody로 외부에서 데이터를 받는 경우엔 기본생성자 + set메소드를 통해서만 값이 할당됩니다.
//이때만 setter를 허용.
@Setter
@NoArgsConstructor

//여기서 Entity 클래스와 거의 유사한 형태임에도 DTO 클래스를 추가로 생성.
//절대로 테이블과 매핑되는 Entity 클래스를 Request/ Response 클래스로 사용해서는 안됨.
//Entity 클래스는 가장 Core한 클래스라고 보시면 되는데요.
//수많은 서비스 클래스나 비지니스 로직들이 Entity 클래스를 기준으로 동작합니다.
//View Layer와 DB Layer를 철저하게 역할 분리를 하는게 좋습니다.
public class MemberSaveRequestDto {

    private String memberId;

    private String memberPassword;

    private String memberEmail;

    private String memberGender;


    public Member toEntity(){
        return Member.builder()
                .memberId(memberId)
                .memberPassword(memberPassword)
                .memberEmail(memberEmail)
                .memberGender(memberGender)
                .build();
    }
}
