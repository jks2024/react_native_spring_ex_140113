package com.example.react_native_spring_ex_140113.controller;

import com.example.react_native_spring_ex_140113.dto.ChatRoomReqDto;
import com.example.react_native_spring_ex_140113.dto.ChatRoomResDto;
import com.example.react_native_spring_ex_140113.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomDto) {
        log.warn("chatRoomDto : {}", chatRoomDto);
        ChatRoomResDto room = chatService.createRoom(chatRoomDto.getName());
        System.out.println(room.getRoomId());
        return ResponseEntity.ok(room.getRoomId());
    }
    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomResDto>> findAllRoom() {
        return ResponseEntity.ok(chatService.findAllRoom());
    }

    // 방 정보 가져오기
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomResDto> findRoomById(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.findRoomById(roomId));
    }
}
