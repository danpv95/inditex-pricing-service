package com.bcnc.inditex_pricing_service.infrastructure.persistence.mapper;

import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.infrastructure.persistence.entity.PriceEntity;
import org.springframework.stereotype.Component;

@Component
public class PriceEntityMapper {

    public Price toDomain(PriceEntity entity) {
        return Price.of(
                entity.getId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }

    public PriceEntity toEntity(Price price) {
        return new PriceEntity(
                price.getBrandId(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPriceList(),
                price.getProductId(),
                price.getPriority(),
                price.getPrice(),
                price.getCurrency()
        );
    }

}

