package com.example.lastproject.domain.chat.service;

import com.example.lastproject.common.dto.AuthUser;
import com.example.lastproject.domain.chat.dto.ChatRoomResponse;

import java.util.List;

public interface ChatRoomService {

    ChatRoomResponse createChatRoom(Long partyId, AuthUser authUser);

    List<ChatRoomResponse> getChatRooms(AuthUser authUser);

    void deleteChatRoom(Long chatRoomId, AuthUser authUser);

}
