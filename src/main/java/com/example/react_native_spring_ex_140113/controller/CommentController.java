package com.example.react_native_spring_ex_140113.controller;

import com.example.react_native_spring_ex_140113.dto.CommentDto;
import com.example.react_native_spring_ex_140113.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> commentRegister(@RequestBody CommentDto commentDto) {
        log.info("commentDto: {}", commentDto);
        boolean result = commentService.commentRegister(commentDto);
        return ResponseEntity.ok(result);
    }
    // 댓글 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> commentModify(@RequestBody CommentDto commentDto) {
        boolean result = commentService.commentModify(commentDto);
        return ResponseEntity.ok(result);
    }
    // 댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Boolean> commentDelete(@PathVariable Long commentId) {
        boolean result = commentService.commentDelete(commentId);
        return ResponseEntity.ok(result);
    }
    // 댓글 목록 조회
    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<CommentDto>> commentList(@PathVariable Long boardId) {
        log.info("boardId: {}", boardId);
        List<CommentDto> list = commentService.getCommentList(boardId);
        return ResponseEntity.ok(list);
    }

    // 댓글 상세 조회
    // 댓글 검색
    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> commentSearch(@RequestParam String keyword) {
        List<CommentDto> list = commentService.getCommentList(keyword);
        return ResponseEntity.ok(list);
    }
}
