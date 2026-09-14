package com.estaciona_ai.garages;

import com.estaciona_ai.users.UserEntity;
import com.estaciona_ai.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GarageService {

    private final GarageRepository garageRepository;
    private final UserRepository userRepository;
    private final GarageMapper garageMapper;

    // Create garage method
    @Transactional
    public GarageResponse createGarage(GarageRequest garageReq, UUID ownerId) {
        UserEntity owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado"));
        GarageEntity garage = garageMapper.toEntity(garageReq);
        garage.setOwner(owner);
        return garageMapper.toResponse(
                garageRepository.save(garage)
        );
    }

    @Transactional(readOnly = true)
    public GarageResponse getGarageById(UUID garageId) {
        return garageRepository.findById(garageId)
                .map(garageMapper::toResponse)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Garagem não encontrada com o ID: " + garageId
                        ));
    }

    @Transactional(readOnly = true)
    public List<GarageResponse> getGaragesByOwner(UUID ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new EntityNotFoundException(
                    "Usuário não encontrado com o ID: " + ownerId
            );
        }

        return garageMapper.toResponseList(
                garageRepository.findByOwnerId(ownerId)
        );
    }

    @Transactional
    public GarageResponse updateGarageById(
            UUID garageId,
            GarageRequest garageReq,
            UUID ownerId
    ) {
        GarageEntity garage = garageRepository.findById(garageId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Garagem não encontrada com o ID: " + garageId
                        ));
        if (!garage.getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException(
                    "Você não tem permissão para alterar os dados dessa garagem"
            );
        }
        garageMapper.updateEntityFromDto(garageReq, garage);
        return garageMapper.toResponse(
                garageRepository.save(garage)
        );
    }

    @Transactional
    public void deleteGarageById(UUID garageId, UUID ownerId) {
        GarageEntity garage = garageRepository.findById(garageId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Garagem não encontrada com o ID: " + garageId
                        ));
        if (!garage.getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException(
                    "Você não tem permissão para deletar esta garagem"
            );
        }
        garageRepository.delete(garage);
    }
}
