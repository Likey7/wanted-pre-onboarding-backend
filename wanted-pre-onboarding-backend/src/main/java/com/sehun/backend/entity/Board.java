package com.sehun.backend.entity;

import javax.persistence.*;

import com.sehun.backend.dto.BoardDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer num;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content; 

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="writer", nullable = false)  
    private Member member; 

    // DTO를 entity로 변환
    @Builder
    public Board(BoardDTO boardDTO, Member member) {
        this.num = boardDTO.getNum();
        this.title = boardDTO.getTitle();
        this.content = boardDTO.getContent();
        this.member = member;
    }
    
    //게시글 수정시 setContent
    public void setContent(String content) {
        this.content = content;
    }
}
