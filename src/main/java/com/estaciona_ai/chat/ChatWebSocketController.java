package com.estaciona_ai.chat;

import com.estaciona_ai.chat.message.ChatMessageRequest;
import com.estaciona_ai.chat.message.ChatMessageResponse;
import com.estaciona_ai.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void processMessage(@Payload ChatMessageRequest payload, Principal principal) {
        // Recupera o usuário autenticado da conexão STOMP
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) principal;
        AuthenticatedUser user = (AuthenticatedUser) authToken.getPrincipal();

        // Opcional: injeta ou valida o senderId garantindo a identidade real do remetente
        ChatMessageResponse response = chatService.saveMessage(payload, user.id());

        messagingTemplate.convertAndSend(
                "/topic/room/" + payload.roomId(),
                response
        );
    }
}