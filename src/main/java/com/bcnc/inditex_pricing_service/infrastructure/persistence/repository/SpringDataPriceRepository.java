package com.bcnc.inditex_pricing_service.infrastructure.persistence.repository;

import com.bcnc.inditex_pricing_service.infrastructure.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByProductIdAndBrandIdOrderByPriorityDescStartDateDesc(
            Long productId, Long brandId);
}

