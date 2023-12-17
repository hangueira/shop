package com.hks.shop.config;

import com.hks.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    MemberService memberService;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        // 로그인 설정
//        http.formLogin()
//                .loginPage("/user2/login")
//                .defaultSuccessUrl("/user2/loginSuccess")
//                .failureUrl("/user2/login?success=100)")
//                .usernameParameter("uid")
//                .passwordParameter("pass");
//
//        // 로그아웃 설정
//        http.logout()
//                .invalidateHttpSession(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
//                .logoutSuccessUrl("/user2/login?success=200");
//
//        // 사용자 인증 처리 컴포넌트 서비스 등록
//        http.userDetailsService(service);
//
//        return http.build();
//
//        @Bean
//        public PasswordEncoder passwordEncoder () {
//            return new BCryptPasswordEncoder();
//        }
    }
