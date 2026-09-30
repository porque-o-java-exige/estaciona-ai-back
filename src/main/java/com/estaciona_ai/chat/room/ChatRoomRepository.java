package com.estaciona_ai.chat.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, UUID> {
    Optional<ChatRoomEntity> findByBookingId(UUID bookingId);
    List<ChatRoomEntity> findByDriverIdOrOwnerId(UUID driverId, UUID ownerId);
}
