package com.estaciona_ai.users;

import com.estaciona_ai.enums.RolesEnum;
import com.estaciona_ai.enums.StatusEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest userReq){
        if(userReq == null){
            throw new IllegalArgumentException("os dados inseridos estão inválidos");
        }
        if(userRepository.findByEmail(userReq.email()).isPresent()){
            throw new DuplicateKeyException("já existe um usuário com esse email");
        }
        UserEntity newUser = userMapper.toEntity(userReq);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setRoles(RolesEnum.ROLE_USER);
        newUser.setStatus(StatusEnum.ACTIVE);
        userRepository.save(newUser);
        return userMapper.toResponse(newUser);
    }

    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return userMapper.toResponseList(users);
    }

    public UserResponse getUserById(UUID id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
        return userMapper.toResponse(user);
    }

    public UserResponse updateUser(UUID id, UserRequest userReq){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + id));
        userMapper.updateEntityFromDto(userReq, user);
        user.setUpdatedAt((LocalDateTime.now()));
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    public void deleteUserById(UUID id){
        userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("não existe um usuário com esse id"));
        userRepository.deleteById(id);
    }
}
