package com.estaciona_ai.users;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        userRepository.save(newUser);
        return userMapper.toResponse(newUser);
    }

    public List<UserResponse> showAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return userMapper.toResponseList(users);
    }

}
