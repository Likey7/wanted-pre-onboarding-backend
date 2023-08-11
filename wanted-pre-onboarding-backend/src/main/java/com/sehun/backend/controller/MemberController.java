package com.sehun.backend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sehun.backend.dto.MemberDTO;
import com.sehun.backend.security.TokenInfo;
import com.sehun.backend.service.MemberService;

@RestController
public class MemberController {
    
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @Autowired
    public MemberController(PasswordEncoder passwordEncoder, MemberService memberService) {
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
    }

    //회원가입 메소드
    @PostMapping("/join")
    public ResponseEntity<String> join(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (accumulator, errorMessageItem) -> accumulator + ", " + errorMessageItem);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        try {
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
            memberService.join(memberDTO);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //로그인 메소드
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (accumulator, errorMessageItem) -> accumulator + ", " + errorMessageItem);
            response.put("error", errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            TokenInfo tokenInfo = memberService.login(memberDTO.getEmail(), memberDTO.getPassword());
            response.put("tokenInfo", tokenInfo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
