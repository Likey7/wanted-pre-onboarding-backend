package com.sehun.backend.security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sehun.backend.entity.Member;
import com.sehun.backend.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
private final MemberRepository memberRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return memberRepository.findByEmail(username)
				.map(this::createUserDetails)
				.orElseThrow(()-> new UsernameNotFoundException("유저 정보 없음"));
	}

	private UserDetails createUserDetails(Member member) {
		return User.builder()
				.username(member.getUsername())
				.password(member.getPassword())
				.roles(member.getRoles().toArray(new String[0]))
				.build();
	
	}
}
