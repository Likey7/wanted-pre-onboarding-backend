package com.sehun.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {

	  public static String getCurrentMemberId() {
	        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication == null || authentication.getName() == null) {
	            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
	        }
	        String memberId = authentication.getName(); 
	        
	        return memberId;
	    }
	  
	    
}
