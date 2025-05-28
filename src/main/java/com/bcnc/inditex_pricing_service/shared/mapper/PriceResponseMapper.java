package com.bcnc.inditex_pricing_service.shared.mapper;

import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.shared.dto.PriceResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PriceResponseMapper {

    public PriceResponseDto toDto(Price price) {
        return new PriceResponseDto(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurrency()
        );
    }
}
