package com.estaciona_ai.notifications;

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
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "Endpoints para consulta e leitura de notificações enviadas aos usuários")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Listar Notificações do Usuário", description = "Retorna o histórico de notificações recebidas por um usuário específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(
            @Parameter(description = "ID do Usuário", example = "8f07e730-23e1-47b4-a20e-f667b0961303") @PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @Operation(summary = "Marcar Notificação como Lida", description = "Altera o status do campo isRead da notificação para true.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificação atualizada para lida com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    })
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @Parameter(description = "ID da Notificação", example = "3b07e730-23e1-47b4-a20e-f667b0961203") @PathVariable UUID id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}