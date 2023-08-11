package com.sehun.backend.dto;

import com.sehun.backend.entity.Board;
import com.sehun.backend.entity.Member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardDTO { 
    private int num;
    private String title;
    private String content;
    private String writer;

    //entity를 DTO로 변환
    @Builder
    public BoardDTO(Board board) {
        this.num = board.getNum();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getMember().getEmail();
    }

    //생성자
    public BoardDTO(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.writer = member.getEmail();
    }
}
