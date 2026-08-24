package com.estaciona_ai.users;

import com.estaciona_ai.enums.RolesEnum;
import com.estaciona_ai.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    /*private String photoUrl;*/
    @Enumerated(EnumType.STRING)
    private RolesEnum roles;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
