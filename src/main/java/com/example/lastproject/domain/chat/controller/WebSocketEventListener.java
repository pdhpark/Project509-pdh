package com.example.lastproject.domain.chat.controller;

import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.chat.dto.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import java.util.Objects;

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
     * SessionConnectedEvent가 아닌 SessionConnectEvent사용
     * @return : '사용자이메일 joined!'라는 입장메세지 전송
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {

        // StompHeaderAccessor를 사용하여 세션 정보 접근
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //JwtInterceptor에서 검증한 뒤 헤더에 저장해놓은 유저객체에서 email 추출
        AuthUser authUser = (AuthUser) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("authUser");
        String email = authUser.getEmail();

        //javaScript에서 헤더로 받아올수 있게 설정한 chatRoomId 추출
        long chatRoomId = Long.parseLong(Objects.requireNonNull(headerAccessor.getFirstNativeHeader("chatRoomId")));

        // 세션 속성에 chatRoomId 저장(아래 SessionDisconnectEvent에서 사용하기 위함. 저장해놓지 않으면 SessionDisconnectEvent에서 사용할 수 없음)
        headerAccessor.getSessionAttributes().put("chatRoomId", chatRoomId);

        //메세지 전송
        if (email != null) {

            ChatMessageRequest chatMessage = new ChatMessageRequest();
            chatMessage.changeType(ChatMessageRequest.MessageType.JOIN); // JOIN 타입 설정
            chatMessage.changeSender(email);

            messagingTemplate.convertAndSend("/topic/" + chatRoomId, chatMessage);

        }

    }

    /**
     * 유저가 나갔을 때 호출되는 메서드
     * @return : '사용자이메일 left!'라는 퇴장메세지 전송
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        //StompHeaderAccessor를 사용하여 세션 정보 접근
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //헤더에서 채팅방을 나간 유저의 email과 chatRoomId를 추출
        AuthUser authUser = (AuthUser) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("authUser");
        String email = authUser.getEmail();
        Long chatRoomId = (Long) headerAccessor.getSessionAttributes().get("chatRoomId");

        //메세지 전송
        if(email != null) {

            ChatMessageRequest chatMessage = new ChatMessageRequest();
            chatMessage.changeType(ChatMessageRequest.MessageType.LEAVE); //LEAVE타입 설정
            chatMessage.changeSender(email);

            messagingTemplate.convertAndSend("/topic/"+chatRoomId, chatMessage);

        }

    }

}
