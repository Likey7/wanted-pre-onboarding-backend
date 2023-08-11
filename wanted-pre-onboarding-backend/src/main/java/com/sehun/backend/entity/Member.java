package com.sehun.backend.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sehun.backend.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails {
	private static final long serialVersionUID = 1L;

    @Id
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY) // 'Board'와의 일대다 관계를 정의, 필요할 때 데이터를 가져오는 지연 로딩을 사용
    @Builder.Default // Lombok의 빌더 패턴에서 'boards' 필드의 기본값으로 사용
    private List<Board> boards = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Member(MemberDTO memberDTO) {
        this.email = memberDTO.getEmail();
        this.password = memberDTO.getPassword();

    }

    //return : 사용자의 모든 권한을 나타내는 GrantedAuthority 객체의 컬렉션
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
