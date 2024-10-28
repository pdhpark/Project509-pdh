package com.example.lastproject.domain.chat.service;

import com.example.lastproject.domain.chat.dto.ChatMessageDto;
import com.example.lastproject.domain.chat.entity.ChatMessage;
import com.example.lastproject.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    /**
     * 입력한 채팅메세지를 DB에 저장 후 반환하는 메서드
     * @param roomId : 채팅방 Id
     * @param chatMessageDto : 채팅타입, 내용, 보낸사람
     * @return : 입력된 채팅메세지
     */
    public ChatMessageDto sendMessage(String roomId, ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = new ChatMessage(roomId, chatMessageDto);
        chatMessageRepository.save(chatMessage);
        return chatMessageDto;
    }

    /**
     * 새로운 사용자가 채팅방에 입장했을 때, 입장 전 기록된 채팅들을 보여주기 위한 메서드
     * @param roomId
     * @return
     */
    public List<ChatMessage> getChatHistory(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAt(roomId);
    }

}
