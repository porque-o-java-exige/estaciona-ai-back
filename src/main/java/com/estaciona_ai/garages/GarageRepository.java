package com.estaciona_ai.garages;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface GarageRepository extends JpaRepository<GarageEntity, UUID> {

    List<GarageEntity> findByOwnerId(UUID ownerId);
    List<GarageEntity> findByAvailableTrue();
    @Query(value = """
        SELECT g.* FROM garages g
        WHERE g.available = true 
          AND (6371 * acos(
                cos(radians(:lat)) * cos(radians(g.latitude)) * 
                cos(radians(g.longitude) - radians(:lng)) + 
                sin(radians(:lat)) * sin(radians(g.latitude))
              )) <= :radius
        ORDER BY (6371 * acos(
                cos(radians(:lat)) * cos(radians(g.latitude)) * 
                cos(radians(g.longitude) - radians(:lng)) + 
                sin(radians(:lat)) * sin(radians(g.latitude))
              )) ASC
        """,
            countQuery = """
        SELECT count(*) FROM garages g
        WHERE g.available = true 
          AND (6371 * acos(
                cos(radians(:lat)) * cos(radians(g.latitude)) * 
                cos(radians(g.longitude) - radians(:lng)) + 
                sin(radians(:lat)) * sin(radians(g.latitude))
              )) <= :radius
        """,
            nativeQuery = true)
    Page<GarageEntity> findNearby(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") double radius,
            Pageable pageable
    );
}
