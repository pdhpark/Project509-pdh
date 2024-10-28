package com.example.lastproject.domain.chat.controller;

import com.example.lastproject.domain.chat.dto.ChatMessageDto;
import com.example.lastproject.domain.chat.entity.ChatMessage;
import com.example.lastproject.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    /**
     * 경로를 "/app/chat.sendMessage"로 가지고있는 메세지들이 다뤄지는 메서드
     * @return 입력한 메세지 저장 후 반환
     * return값을 @SendTo의 파라미터에 적힌 토픽으로 보냄
     */
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessageDto sendMessage(@DestinationVariable("roomId") String roomId, @Payload ChatMessageDto chatMessage) {
        return chatMessageService.sendMessage(roomId, chatMessage);
    }

    /**
     * 경로를 "/app/chat.addUser"로 가지고있는 메세지들이 다뤄지는 메서드
     * @return "00님이 입장하셨습니다"와 같은 Greeting 메세지
     * return값을 @SendTo의 파라미터에 적힌 토픽으로 보냄
     */
    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessageDto addUser(@DestinationVariable("roomId") String roomId, @Payload ChatMessageDto chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // web socket session에 username, roomId 추가
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        return chatMessage;
    }

    /**
     * 채팅방에 사용자가 입장했을 때, 입장 전에 존재했던 채팅 메세지들을 보여주는 메서드
     * @param roomId
     * @return
     */
    @GetMapping("/chat/history/{roomId}")
    @ResponseBody
    public List<ChatMessage> getChatHistory(@PathVariable String roomId) {
        return chatMessageService.getChatHistory(roomId);
    }

}
