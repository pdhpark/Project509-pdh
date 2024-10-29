package com.example.lastproject.domain.chat.repository;

import com.example.lastproject.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByPartyId(Long party_id);
    boolean existsByPartyId(Long party_id);

}
