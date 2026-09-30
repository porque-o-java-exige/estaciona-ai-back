package com.estaciona_ai.chat;

import com.estaciona_ai.chat.message.ChatMessageResponse;
import com.estaciona_ai.chat.room.ChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<ChatRoomResponse> getOrCreateRoom(
            @PathVariable UUID bookingId,
            @RequestHeader("x-user-id") UUID userId
    ) {
        return ResponseEntity.ok(chatService.getOrCreateRoomForBooking(bookingId, userId));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> getMyRooms(
            @RequestHeader("x-user-id") UUID userId
    ) {
        return ResponseEntity.ok(chatService.getUserRooms(userId));
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
            @PathVariable UUID roomId,
            @RequestHeader("x-user-id") UUID userId
    ) {
        return ResponseEntity.ok(chatService.getRoomMessages(roomId, userId));
    }
}