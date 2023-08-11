package com.sehun.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sehun.backend.dto.BoardDTO;
import com.sehun.backend.entity.Board;
import com.sehun.backend.entity.Member;
import com.sehun.backend.repository.BoardRepository;
import com.sehun.backend.security.JwtUtil;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberService memberService;

    @Override
    public void writeBoard(BoardDTO boardDTO) throws Exception {
        Member member = getCurrentMember();
        boardRepository.save(new Board(boardDTO, member));
    }

    @Override
    public BoardDTO modifyBoard(int num, String content) throws Exception {
        Member member = getCurrentMember();
        Board board = getBoardById(num);
        
        if (!member.getEmail().equals(board.getMember().getEmail())) {
            throw new Exception("수정권한이 없습니다.");
        }

        board.setContent(content);
        boardRepository.save(board);

        return new BoardDTO(board);
    }

    @Override
    public void removeBoard(int num) throws Exception {
        Member member = getCurrentMember();
        Board board = getBoardById(num);

        if (!member.getEmail().equals(board.getMember().getEmail())) {
            throw new Exception("삭제권한이 없습니다.");
        }

        boardRepository.delete(board);
    }

    @Override
    public List<BoardDTO> getBoardList(int page, Integer reqCnt) throws Exception {
        List<BoardDTO> list = new ArrayList<>();
        
        reqCnt = (reqCnt == null) ? 10 : reqCnt;
        PageRequest pageRequest = PageRequest.of(page - 1, reqCnt, Sort.by(Sort.Direction.DESC, "num"));
        Page<Board> boards = boardRepository.findAll(pageRequest);

        boards.forEach(b -> list.add(new BoardDTO(b)));

        return list;
    }

    @Override
    public BoardDTO searchBoard(int num) throws Exception {
        Board board = getBoardById(num);
        return new BoardDTO(board);
    }

    private Member getCurrentMember() throws Exception {
        String email = JwtUtil.getCurrentMemberId();
        return memberService.getMember(email);
    }

    private Board getBoardById(int num) throws Exception {
        return boardRepository.findById(num).orElseThrow(() -> new Exception("해당하는 게시글이 없습니다."));
    }
}
