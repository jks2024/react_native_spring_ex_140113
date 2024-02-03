package com.example.react_native_spring_ex_140113.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessageDto {
    public enum MessageType {
        ENTER, TALK, CLOSE
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}