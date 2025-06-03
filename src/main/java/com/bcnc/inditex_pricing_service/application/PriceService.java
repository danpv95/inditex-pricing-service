package com.bcnc.inditex_pricing_service.application;

import com.bcnc.inditex_pricing_service.domain.exception.NoApplicablePriceException;
import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.domain.port.input.PriceUseCase;
import com.bcnc.inditex_pricing_service.domain.port.output.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PriceService implements PriceUseCase {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceRepository.findByProductIdAndBrandId(productId, brandId, applicationDate)
                .orElseThrow(() -> new NoApplicablePriceException(productId, brandId));
    }

}
