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

    //SpringSecurity에서 유저인증 ,로그인 인증처리를 구현(비밀번호 대조)하는 객체
    @Autowired
    private AuthenticationManager authenticationManager;

    private MemberRepository memberRepository;

    // 로그인 기능 구현
    // path : /user/login
    // Method : POST
    @RequestMapping(value="/login", method= RequestMethod.POST) //
    // @RequestBody 를 통해 Body의 데이터를 해당 객체로 받습니다. JSON 형태로 받는 경우 객체로 자동 매핑 된다.
    // session 을 파라미터로 지정하면 받아 올 수 있다.
    public AuthenticationToken login(@RequestBody AuthenticationRequest authenticationRequest,
                                     HttpSession session){

        String memberId = authenticationRequest.getMemberId();
        String password = authenticationRequest.getPassword();

        //Authentication 인터페이스를 구현한 UsernamePasswordAuthenticationToken으로 token 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberId, password);
        //AuthenticationManager의 authenticate 메소드에 위에서 만든 token을 파라미터로 하여 인증을 진행
        Authentication authentication = authenticationManager.authenticate(token);
        //인증받은 결과를 SecurityContextHolder에서 getContext()를 통해 context를 받아온 후, 이 context에 인증 결과를 set
        //이로써 서버의 SecurityContext에는 인증값이 설정.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //session 속성값에 SecurityContext 값을 넣어준다.
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        Member member = memberRepository.findByMemberId(memberId);
        //인증 완료 후 AuthenticationToken 객체 리턴
        return new AuthenticationToken(member.getMemberId(), member.getRoles(), session.getId());
    }

    //회원가입 기능 구현
    // path : /user/register
    // Method : POST
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
