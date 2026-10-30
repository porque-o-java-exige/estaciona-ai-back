package com.estaciona_ai.auth;

import com.estaciona_ai.security.JwtService;
import com.estaciona_ai.users.UserEntity;
import com.estaciona_ai.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthMapper authMapper;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("E-mail ou senha inválidos");
        }

        String token = jwtService.generateToken(user);
        return authMapper.toLoginResponse(user, token, "Bearer");
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}