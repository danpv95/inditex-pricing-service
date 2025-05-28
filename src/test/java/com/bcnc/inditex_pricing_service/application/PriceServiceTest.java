package com.bcnc.inditex_pricing_service.application;

import com.bcnc.inditex_pricing_service.domain.exception.NoApplicablePriceException;
import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.domain.port.output.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private PriceRepository repository;
    private PriceService service;

    @BeforeEach
    void setUp() {
        repository = mock(PriceRepository.class);
        service = new PriceService(repository);
    }

    @Test
    void shouldReturnHighestPriorityPrice() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price lowerPriority = Price.of(null, brandId, date.minusHours(1), date.plusHours(2),
                1, productId, 0, new BigDecimal("35.50"), "EUR");

        Price higherPriority = Price.of(null, brandId, date.minusHours(30), date.plusHours(30),
                2, productId, 1, new BigDecimal("25.45"), "EUR");

        when(repository.findByProductIdAndBrandId(productId, brandId))
                .thenReturn(List.of(lowerPriority, higherPriority));

        Price result = service.getApplicablePrice(productId, brandId, date);

        assertEquals(2, result.getPriceList());
        assertEquals(new BigDecimal("25.45"), result.getPrice());
    }

    @Test
    void shouldThrowWhenNoPriceApplies() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);

        when(repository.findByProductIdAndBrandId(35455L, 1L)).thenReturn(List.of());

        assertThrows(NoApplicablePriceException.class, () ->
                service.getApplicablePrice(35455L, 1L, date));
    }
}
