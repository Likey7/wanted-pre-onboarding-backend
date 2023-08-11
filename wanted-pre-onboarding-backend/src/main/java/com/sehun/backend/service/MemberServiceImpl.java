// MemberServiceImpl
package com.sehun.backend.service;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sehun.backend.dto.MemberDTO;
import com.sehun.backend.entity.Member;
import com.sehun.backend.repository.MemberRepository;
import com.sehun.backend.security.JwtProvider;
import com.sehun.backend.security.TokenInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Override
    public void join(MemberDTO memberDTO) throws Exception {
        memberRepository.save(new Member(memberDTO));
    }

    @Override
    public TokenInfo login(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtProvider.createToken(authentication);
    }

    @Override
    public Member getMember(String email) throws Exception {
        Optional<Member> omember = memberRepository.findByEmail(email);
        if (omember.isEmpty()) throw new Exception("유저정보가 없습니다");
        return omember.get();
    }
}