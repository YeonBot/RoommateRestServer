package com.yeonbot.roommate.member;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanup() {
        /**
         이후 테스트 코드에 영향을 끼치지 않기 위해
         테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
         **/
        memberRepository.deleteAll();
    }

    @Test
    public void 회원가입() {
        //given

        for(int i=0; i<100; i++) {

            MemberRole role = new MemberRole();
            if(i <= 80) {
                role.setRoleName("BASIC");
            }else if(i <= 90) {
                role.setRoleName("MANAGER");
            }else {
                role.setRoleName("ADMIN");
            }

            memberRepository.save(Member.builder()
                    .memberId("id"+i)
                    .memberPassword("pw"+i)
                    .memberEmail("email"+i)
                    .memberGender("남성"+i)
                    .roles(Arrays.asList(role))
                    .build());
        }

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Optional<Member> result = Optional.ofNullable(memberList.get(85));
    }


}