package com.estaciona_ai.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    public AuthenticatedUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser)) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado no contexto de segurança");
        }
        return (AuthenticatedUser) authentication.getPrincipal();
    }

    public UUID getAuthenticatedUserId() {
        return getAuthenticatedUser().id();
    }
}