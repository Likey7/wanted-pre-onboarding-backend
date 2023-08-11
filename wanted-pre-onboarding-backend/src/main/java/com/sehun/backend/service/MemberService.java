// MemberService Interface
package com.sehun.backend.service;

import com.sehun.backend.dto.MemberDTO;
import com.sehun.backend.entity.Member;
import com.sehun.backend.security.TokenInfo;

public interface MemberService {
    void join(MemberDTO memberDTO) throws Exception;
    TokenInfo login(String email, String password) throws Exception;
    Member getMember(String email) throws Exception;
}