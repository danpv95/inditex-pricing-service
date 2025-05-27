package com.bcnc.inditex_pricing_service.infrastructure.persistence.repository;

import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.domain.port.output.PriceRepository;
import com.bcnc.inditex_pricing_service.infrastructure.persistence.mapper.PriceEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Price> findByProductIdAndBrandId(Long productId, Long brandId) {
        return repository
                .findByProductIdAndBrandIdOrderByPriorityDescStartDateDesc(productId, brandId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
