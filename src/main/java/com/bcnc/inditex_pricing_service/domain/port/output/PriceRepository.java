package com.bcnc.inditex_pricing_service.domain.port.output;

import com.bcnc.inditex_pricing_service.domain.model.Price;

import java.util.List;

public interface PriceRepository {
    List<Price> findByProductIdAndBrandId(Long productId, Long brandId);
}
