package com.sehun.backend.service;

import java.util.List;

import com.sehun.backend.dto.BoardDTO;

public interface BoardService {

    void writeBoard(BoardDTO boardDTO) throws Exception;

    List<BoardDTO> getBoardList(int num, Integer reqCnt) throws Exception;

    BoardDTO searchBoard(int num) throws Exception;

    BoardDTO modifyBoard(int num, String content) throws Exception;

    void removeBoard(int num) throws Exception;
}
