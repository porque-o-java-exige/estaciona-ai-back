package com.estaciona_ai.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuários e atualização de foto de perfil")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Cadastrar Usuário", description = "Cria um novo usuário com senha criptografada. Endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado no sistema")
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userReq) {
        UserResponse createdUser = userService.createUser(userReq);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @Operation(summary = "Listar todos os Usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar Usuário por ID", description = "Obtém os detalhes de um usuário específico pelo ID.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID do Usuário", example = "8f07e730-23e1-47b4-a20e-f667b0961303") @PathVariable UUID id) {
        UserResponse userRes = userService.getUserById(id);
        return ResponseEntity.ok(userRes);
    }

    @Operation(summary = "Atualizar Usuário", description = "Atualiza os dados cadastrais de um usuário pelo ID.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID do Usuário", example = "8f07e730-23e1-47b4-a20e-f667b0961303") @PathVariable UUID id,
            @RequestBody UserRequest userReq) {
        UserResponse userRes = userService.updateUser(id, userReq);
        return ResponseEntity.ok(userRes);
    }

    @Operation(summary = "Excluir Usuário", description = "Remove o cadastro de um usuário do sistema.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do Usuário", example = "8f07e730-23e1-47b4-a20e-f667b0961303") @PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar Foto de Perfil", description = "Faz upload da foto do perfil para o Azure Storage e atualiza a URL do usuário.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto enviada e vinculada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Arquivo enviado inválido"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping(value = "/{id}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> updateUserPhoto(
            @Parameter(description = "ID do Usuário", example = "8f07e730-23e1-47b4-a20e-f667b0961303") @PathVariable UUID id,
            @RequestParam("file") MultipartFile file
    ) {
        UserResponse updatedUser = userService.updateUserPhoto(id, file);
        return ResponseEntity.ok(updatedUser);
    }
}