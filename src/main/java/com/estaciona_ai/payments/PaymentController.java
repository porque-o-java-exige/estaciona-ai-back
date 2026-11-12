package com.estaciona_ai.payments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pagamentos", description = "Geração de cobranças Pix e integração Mercado Pago")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Criar Pagamento Pix", description = "Gera a cobrança no Mercado Pago e devolve o código Copia e Cola e o QR Code em Base64.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cobrança gerada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor total inválido ou erro no Mercado Pago"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.createPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar Pagamento por ID", description = "Consulta os detalhes de um pagamento efetuado ou pendente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @Parameter(description = "ID do Pagamento", example = "1c07e730-23e1-47b4-a20e-f667b0961999") @PathVariable UUID id) {

        PaymentResponse response = paymentService.getPaymentById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Simular Aprovação de Pagamento", description = "Simula a aprovação do pagamento Pix alterando o status para APPROVED e a reserva para CONFIRMED.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento aprovado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    @PostMapping("/{id}/simulate-approval")
    public ResponseEntity<PaymentResponse> simulateApproval(
            @Parameter(description = "ID do Pagamento", example = "1c07e730-23e1-47b4-a20e-f667b0961999") @PathVariable UUID id) {

        PaymentResponse response = paymentService.simulateApproval(id);

        return ResponseEntity.ok(response);
    }
}