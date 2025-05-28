package com.bcnc.inditex_pricing_service.domain.port.input;

import com.bcnc.inditex_pricing_service.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceUseCase {
    Price getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
