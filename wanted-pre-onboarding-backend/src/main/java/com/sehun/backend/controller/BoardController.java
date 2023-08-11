package com.sehun.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sehun.backend.dto.BoardDTO;
import com.sehun.backend.service.BoardService;

@RestController
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    
    //게시글 작성 
    @PostMapping("/write")
    public ResponseEntity<String> newBoard(@RequestBody BoardDTO newBoardDTO) {
        return processResponse(() -> {
            boardService.writeBoard(newBoardDTO);
            return "게시글 작성 성공";
        });
    }
    
    //게시글 리스트 불러오기
    @GetMapping("/boardList/{page}")
    public ResponseEntity<Map<String, Object>> boardList(@PathVariable Integer page, 
                                                         @RequestParam(value = "reqCnt", required = false) Integer reqCnt) {
        return processResponse(() -> {
            Map<String, Object> response = new HashMap<>();
            List<BoardDTO> list = boardService.getBoardList(page, reqCnt);
            response.put("list", list);
            return response;
        });
    }

    //게시글 상세보기
    @GetMapping("/boardDetail/{id}")
    public ResponseEntity<BoardDTO> searchBoard(@PathVariable Integer id) {
        return processResponse(() -> boardService.searchBoard(id));
    }

    //게시글 수정하기
    @PutMapping("/boardEdit/{id}")
    public ResponseEntity<Map<String, Object>> modifyBoard(@PathVariable Integer id, 
                                                           @RequestBody String content) {
        return processResponse(() -> {
            Map<String, Object> response = new HashMap<>();
            BoardDTO boardDTO = boardService.modifyBoard(id, content); 
            response.put("board", boardDTO);
            return response;
        });
    }

    //게시글 삭제하기
    @DeleteMapping("/boardDelete/{id}")
    public ResponseEntity<String> login(@PathVariable Integer id) {
        return processResponse(() -> {
            boardService.removeBoard(id); 
            return "게시글 삭제 성공";
        });
    }
    
    // Http요청 에러처리
    private <T> ResponseEntity<T> processResponse(ServiceAction<T> action) {
        try {
            return new ResponseEntity<>(action.perform(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @FunctionalInterface
    private interface ServiceAction<T> {
        T perform() throws Exception;
    }
}
