package com.estaciona_ai.chat;

import com.estaciona_ai.bookings.BookingEntity;
import com.estaciona_ai.bookings.BookingRepository;
import com.estaciona_ai.chat.message.ChatMessageEntity;
import com.estaciona_ai.chat.message.ChatMessageRepository;
import com.estaciona_ai.chat.message.ChatMessageRequest;
import com.estaciona_ai.chat.message.ChatMessageResponse;
import com.estaciona_ai.chat.room.ChatRoomEntity;
import com.estaciona_ai.chat.room.ChatRoomRepository;
import com.estaciona_ai.chat.room.ChatRoomResponse;
import com.estaciona_ai.users.UserEntity;
import com.estaciona_ai.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoomResponse getOrCreateRoomForBooking(UUID bookingId, UUID requestingUserId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada: " + bookingId));

        UUID driverId = booking.getDriver().getId();
        UUID ownerId = booking.getGarage().getOwner().getId();

        if (!requestingUserId.equals(driverId) && !requestingUserId.equals(ownerId)) {
            throw new IllegalArgumentException("Apenas o motorista ou o dono podem acessar esta sala de chat.");
        }

        ChatRoomEntity room = chatRoomRepository.findByBookingId(bookingId)
                .orElseGet(() -> {
                    ChatRoomEntity newRoom = new ChatRoomEntity();
                    newRoom.setBooking(booking);
                    newRoom.setDriver(booking.getDriver());
                    newRoom.setOwner(booking.getGarage().getOwner());
                    return chatRoomRepository.save(newRoom);
                });

        return toRoomResponse(room);
    }

    @Transactional
    public ChatMessageResponse saveMessage(ChatMessageRequest payload) {
        ChatRoomEntity room = chatRoomRepository.findById(payload.roomId())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada: " + payload.roomId()));

        UserEntity sender = userRepository.findById(payload.senderId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + payload.senderId()));

        ChatMessageEntity message = new ChatMessageEntity();
        message.setChatRoom(room);
        message.setSender(sender);
        message.setContent(payload.content());

        ChatMessageEntity saved = chatMessageRepository.save(message);

        return new ChatMessageResponse(
                saved.getId(),
                room.getId(),
                sender.getId(),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponse> getUserRooms(UUID userId) {
        return chatRoomRepository.findByDriverIdOrOwnerId(userId, userId)
                .stream()
                .map(this::toRoomResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getRoomMessages(UUID roomId, UUID userId) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        if (!room.getDriver().getId().equals(userId) && !room.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("Acesso negado a este chat.");
        }

        return chatMessageRepository.findByChatRoomIdOrderByCreatedAtAsc(roomId)
                .stream()
                .map(m -> new ChatMessageResponse(m.getId(), roomId, m.getSender().getId(), m.getContent(), m.getCreatedAt()))
                .toList();
    }

    private ChatRoomResponse toRoomResponse(ChatRoomEntity room) {
        return new ChatRoomResponse(
                room.getId(),
                room.getBooking().getId(),
                room.getDriver().getId(),
                room.getOwner().getId(),
                room.getCreatedAt()
        );
    }
}