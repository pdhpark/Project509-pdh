package com.example.lastproject.domain.chat.controller;

import com.example.lastproject.domain.chat.dto.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * 소켓의 연결과 해제를 감지하기위한 이벤트리스너를 사용하는 클래스
 */

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * 새로운 유저가 들어왔을 때 호출되는 메서드
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    /**
     * 유저가 나갔을 때 호출되는 메서드
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        //STOMP 메시지의 헤더 정보를 쉽게 다룰 수 있는 유틸리티 클래스를 사용.
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //채팅방을 나간 유저의 이름과 chatRoomId를 추출
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        Long chatRoomId = (Long) headerAccessor.getSessionAttributes().get("chatRoomId");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            ChatMessageRequest chatMessage = new ChatMessageRequest();
            chatMessage.changeType(ChatMessageRequest.MessageType.LEAVE);
            chatMessage.changeSender(username);

            //해당 채팅방에 Leave메세지 전송
            messagingTemplate.convertAndSend("/topic/"+chatRoomId, chatMessage);
        }
    }

}
