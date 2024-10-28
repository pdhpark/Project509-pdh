package com.example.lastproject.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageDto {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public void changeType(MessageType type) {
        this.type = type;
    }

    public void changeSender(String sender) {
        this.sender = sender;
    }
}
