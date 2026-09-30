package com.estaciona_ai.chat;

import com.estaciona_ai.chat.message.ChatMessageRequest;
import com.estaciona_ai.chat.message.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void processMessage(@Payload ChatMessageRequest payload) {
        ChatMessageResponse response = chatService.saveMessage(payload);

        messagingTemplate.convertAndSend(
                "/topic/room/" + payload.roomId(),
                response
        );
    }
}