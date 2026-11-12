package com.estaciona_ai.chat;

import com.estaciona_ai.chat.message.ChatMessageResponse;
import com.estaciona_ai.chat.room.ChatRoomResponse;
import com.estaciona_ai.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Chat", description = "Salas de conversa e histórico de mensagens entre motorista e dono da garagem")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;
    private final SecurityUtils securityUtils;

    @Operation(summary = "Obter ou Criar Sala de Chat", description = "Abre a sala de chat associada a uma reserva. Apenas o motorista ou o dono da garagem podem acessar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala retornada ou criada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado a esta reserva"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<ChatRoomResponse> getOrCreateRoom(
            @Parameter(description = "ID da Reserva", example = "9b07e730-23e1-47b4-a20e-f667b0961309") @PathVariable UUID bookingId) {
        UUID userId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(chatService.getOrCreateRoomForBooking(bookingId, userId));
    }

    @Operation(summary = "Listar Minhas Salas de Chat", description = "Retorna todas as salas de chat ativas do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> getMyRooms() {
        UUID userId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(chatService.getUserRooms(userId));
    }

    @Operation(summary = "Buscar Mensagens da Sala", description = "Retorna o histórico de mensagens de uma sala específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de mensagens retornado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado a esta sala de chat"),
            @ApiResponse(responseCode = "404", description = "Sala de chat não encontrada")
    })
    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
            @Parameter(description = "ID da Sala de Chat", example = "5e07e730-23e1-47b4-a20e-f667b0961805") @PathVariable UUID roomId) {
        UUID userId = securityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(chatService.getRoomMessages(roomId, userId));
    }
}