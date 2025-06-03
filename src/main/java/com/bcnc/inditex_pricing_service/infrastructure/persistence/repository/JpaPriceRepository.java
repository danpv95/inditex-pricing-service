package com.bcnc.inditex_pricing_service.infrastructure.persistence.repository;

import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.domain.port.output.PriceRepository;
import com.bcnc.inditex_pricing_service.infrastructure.persistence.mapper.PriceEntityMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaPriceRepository implements PriceRepository {

    private final SpringDataPriceRepository repository;
    private final PriceEntityMapper mapper;

    public JpaPriceRepository(SpringDataPriceRepository repository,
                              PriceEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Price> findByProductIdAndBrandId(Long productId, Long brandId, LocalDateTime applicationDate) {
        List<Price> results = repository.findByProductIdAndBrandId(productId, brandId, applicationDate)
                .stream()
                .map(mapper::toDomain)
                .toList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

}
