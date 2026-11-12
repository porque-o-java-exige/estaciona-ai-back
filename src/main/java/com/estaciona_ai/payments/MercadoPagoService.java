package com.estaciona_ai.payments;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class MercadoPagoService {

    private final RestClient restClient;

    public MercadoPagoService(
            @Value("${mercadopago.access-token}") String accessToken) {

        this.restClient = RestClient.builder()
                .baseUrl("https://api.mercadopago.com")
                .defaultHeader(
                        "Authorization",
                        "Bearer " + accessToken
                )
                .defaultHeader(
                        "Content-Type",
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    public JsonNode criarPix(
            BigDecimal valor,
            String email,
            String cpf,
            UUID idReserva) {

        Map<String, Object> body = Map.of(
                "transaction_amount", valor,
                "description", "Reserva de garagem - Estaciona AI",
                "payment_method_id", "pix",
                "external_reference", idReserva.toString(),
                "payer", Map.of(
                        "email", email,
                        "identification", Map.of(
                                "type", "CPF",
                                "number", cpf.replaceAll("\\D", "")
                        )
                )
        );

        return restClient.post()
                .uri("/v1/payments")
                .header("X-Idempotency-Key", UUID.randomUUID().toString())
                .body(body)
                .retrieve()
                .body(JsonNode.class);
    }
}