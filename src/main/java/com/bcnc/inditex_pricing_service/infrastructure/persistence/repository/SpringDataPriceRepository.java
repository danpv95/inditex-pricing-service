package com.bcnc.inditex_pricing_service.infrastructure.persistence.repository;

import com.bcnc.inditex_pricing_service.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :productId " +
            "AND p.brandId = :brandId AND :date BETWEEN p.startDate AND p.endDate " +
            "ORDER BY p.priority DESC")
    List<PriceEntity> findByProductIdAndBrandId(@Param("productId") Long productId,
                                           @Param("brandId") Long brandId,
                                           @Param("date") LocalDateTime date);
}

