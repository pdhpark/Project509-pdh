package com.example.lastproject.domain.chat.entity;

import com.example.lastproject.domain.chat.dto.ChatMessageDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;
    private String sender;
    private String content;

    @Enumerated(EnumType.STRING)
    private ChatMessageDto.MessageType type;

    private LocalDateTime createdAt = LocalDateTime.now();

    public ChatMessage(String roomId, ChatMessageDto chatMessageDto) {
        this.roomId = roomId;
        this.sender = chatMessageDto.getSender();
        this.content = chatMessageDto.getContent();
        this.type = chatMessageDto.getType();
    }
}