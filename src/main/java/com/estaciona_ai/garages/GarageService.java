package com.estaciona_ai.garages;

import com.estaciona_ai.users.UserEntity;
import com.estaciona_ai.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GarageService {

    private final GarageRepository garageRepository;
    private final UserRepository userRepository;
    private final GarageMapper garageMapper;

    @Transactional
    public GarageResponse createGarage(GarageRequest garageReq, UUID ownerId) {
        UserEntity owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado"));
        validateGaragePricing(garageReq);

        GarageEntity garage = garageMapper.toEntity(garageReq);
        garage.setOwner(owner);
        syncGarageMediaAndCollections(garageReq, garage);

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
    public List<GarageResponse> getAllAvailableGarages() {
        return garageMapper.toResponseList(garageRepository.findByAvailableTrue());
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

    @Transactional(readOnly = true)
    public Page<GarageNearbyResponse> findNearbyGarages(
            double lat,
            double lng,
            double radius,
            Pageable pageable
    ) {
        Page<GarageEntity> garages = garageRepository.findNearby(lat, lng, radius, pageable);

        return garages.map(garage -> {
            double distance = calculateHaversineDistance(lat, lng, garage.getLatitude(), garage.getLongitude());
            double roundedDistance = BigDecimal.valueOf(distance)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();

            return new GarageNearbyResponse(
                    garage.getId(),
                    garage.getName(),
                    garage.getAddress(),
                    garage.getLatitude(),
                    garage.getLongitude(),
                    garage.getPricePerHour(),
                    garage.getPricePerDay(),
                    garage.getAvailable(),
                    roundedDistance,
                    garage.getCoverImageUrl(),
                    garage.getDescription()
            );
        });
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
        validateGaragePricing(garageReq);

        garageMapper.updateEntityFromDto(garageReq, garage);
        syncGarageMediaAndCollections(garageReq, garage);

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

    private void syncGarageMediaAndCollections(GarageRequest request, GarageEntity garage) {
        garage.setFeatures(
                request.features() != null ? new HashSet<>(request.features()) : new HashSet<>()
        );

        List<String> incomingImages = request.images() != null
                ? new ArrayList<>(request.images())
                : new ArrayList<>();

        String cover = request.coverImageUrl();
        if ((cover == null || cover.isBlank()) && !incomingImages.isEmpty()) {
            cover = incomingImages.get(0);
        } else if (cover != null && !cover.isBlank() && !incomingImages.contains(cover)) {
            incomingImages.add(0, cover);
        }

        garage.setCoverImageUrl(cover);
        garage.setImages(incomingImages);
    }

    private void validateGaragePricing(GarageRequest request) {
        if (request.pricePerHour() == null && request.pricePerDay() == null) {
            throw new IllegalArgumentException("A garagem deve ter pelo menos um preço cadastrado (por hora ou por dia).");
        }
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}