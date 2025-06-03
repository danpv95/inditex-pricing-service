package com.bcnc.inditex_pricing_service.domain.port.output;

import com.bcnc.inditex_pricing_service.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findByProductIdAndBrandId(Long productId, Long brandId, LocalDateTime date);
}
