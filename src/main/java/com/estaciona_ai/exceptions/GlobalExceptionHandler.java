package com.estaciona_ai.exceptions;

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
            error.put("error", "email já cadastrado");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map <String, String>> handleIllegalArgumentException(IllegalArgumentException ex){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Dádos inválidos");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
}
