package com.yeonbot.roommate.member;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @Autowired
    AuthenticationManager authenticationManager;
    private MemberRepository memberRepository;

    //  . @RequestBody를 통해 Body의 데이터를 해당 객체로 받습니다. JSON 형태로 받는 경우 객체로 자동 매핑
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public AuthenticationToken login(@RequestBody AuthenticationRequest authenticationRequest,
                                     HttpSession session){
        String memberId = authenticationRequest.getMemberId();
        String password = authenticationRequest.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberId, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        Member member = memberRepository.findByMemberId(memberId);
        return new AuthenticationToken(member.getMemberId(), member.getRoles(), session.getId());
    }


    //회원가입
    @PostMapping("/register")
    public void create(@RequestBody MemberSaveRequestDto dto) {
        Member member = dto.toEntity();
        MemberRole role = new MemberRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setMemberPassword(passwordEncoder.encode(member.getMemberPassword()));
        role.setRoleName("USER");

        member.setRoles(Arrays.asList(role));
        memberRepository.save(member);
    }




}
