package com.estaciona_ai.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(DuplicateKeyException.class)
        public ResponseEntity<Map<String, String>> handleDuplicateKey(DuplicateKeyException ex){
            Map<String, String> error = new HashMap<>();
            error.put("error", "registro duplicado");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map <String, String>> handleIllegalArgumentException(IllegalArgumentException ex){
            Map<String, String> error = new HashMap<>();
            error.put("error", "dados inválidos");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Map<String, String>> handlerEntityNotFound(EntityNotFoundException ex){
            Map<String, String> error = new HashMap<>();
            error.put("error", "recurso não encontrado");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
}
