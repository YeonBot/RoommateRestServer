package com.yeonbot.roommate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

// WebSecurityConfigurerAdapter를 상속받은 spring security 의
// filter Chain 을 거쳐서, 인증 권한 등 이 클래스에서 지정한대로 수행한다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //의존성을 주입받아서 사용.
    @Autowired
    UserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    //사용자의 쿠키에 세션을 저장하지 않겠다는 옵션 , Rest 아키텍처는 Stateless를 조건으로 하기 때문에 session 정책을 NEVER로 적용
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                    .and()
                .authorizeRequests()
                    .antMatchers("/member/login","/member/register").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/member","/room").hasAuthority("USER")
                    .antMatchers("/admin").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                    .and()
//               .formLogin()
//                    .and()
                .logout();
    }

    //패스워드 인코더 BCryptPasswordEncoder 방식 지정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //설정 사용자의 유저네임과 패스워드가 맞는지 검증해준다.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    //SpringSecurity에서 사용되는 인증객체를 Bean으로 등록할 때 사용
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // HttpSession 전략으로 쿠키의 세션을 사용하는 대신 header에 'x-auth-token' 값을 사용할 수 있게 해준다.
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

}