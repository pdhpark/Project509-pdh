package com.example.lastproject.domain.chat.controller;

import com.example.lastproject.config.JwtAuthenticationToken;
import com.example.lastproject.config.JwtUtil;
import com.example.lastproject.config.SecurityConfig;
import com.example.lastproject.domain.auth.entity.AuthUser;
import com.example.lastproject.domain.chat.dto.ChatMessageRequest;
import com.example.lastproject.domain.chat.dto.ChatMessageResponse;
import com.example.lastproject.domain.chat.service.ChatMessageService;
import com.example.lastproject.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ChatMessageController.class})
@Import({SecurityConfig.class, JwtUtil.class})
class ChatMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatMessageService chatMessageService;

    // Spring Batch에서 사용하는 빈을 Mock 처리하여 테스트에 영향을 주지 않도록 설정합니다.
    @MockBean
    private JobRepository jobRepository;

    // Spring Batch에서 사용하는 빈을 Mock 처리하여 테스트에 영향을 주지 않도록 설정합니다.
    @MockBean
    private JobExplorer jobExplorer;

    // Spring Batch에서 사용하는 빈을 Mock 처리하여 테스트에 영향을 주지 않도록 설정합니다.
    @MockBean
    private JobOperator jobOperator;

    private JwtAuthenticationToken userAuthenticationToken;

    @Test
    public void getChatHistory_성공() throws Exception {

        AuthUser normalUser = new AuthUser(2L, "user@example.com", UserRole.ROLE_USER);
        userAuthenticationToken = new JwtAuthenticationToken(normalUser);

        Long chatRoomId = 1L;

        List<ChatMessageResponse> list = List.of(
                new ChatMessageResponse("sender1", "content1", ChatMessageRequest.MessageType.CHAT),
                new ChatMessageResponse("sender2", "content2", ChatMessageRequest.MessageType.CHAT)
        );

        given(chatMessageService.getChatHistory(chatRoomId)).willReturn(list);

        mockMvc.perform(get("/chat/history/{chatRoomId}", chatRoomId)
                        .with(authentication(userAuthenticationToken)))
                .andExpect(status().isOk());

    }

}