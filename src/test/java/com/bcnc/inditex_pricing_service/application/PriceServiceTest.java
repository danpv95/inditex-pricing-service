package com.bcnc.inditex_pricing_service.application;

import com.bcnc.inditex_pricing_service.domain.exception.NoApplicablePriceException;
import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.domain.port.output.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PriceServiceTest {

    private PriceRepository repository;
    private PriceService service;

    @BeforeEach
    void setUp() {
        repository = mock(PriceRepository.class);
        service = new PriceService(repository);
    }

    @Test
    @DisplayName("Should return applicable price when found in repository")
    void shouldReturnApplicablePriceWhenFound() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price expectedPrice = Price.of(1L, brandId,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2, productId, 1, new BigDecimal("25.45"), "EUR");

        when(repository.findByProductIdAndBrandId(productId, brandId, date))
                .thenReturn(Optional.of(expectedPrice));

        // When
        Price result = service.getApplicablePrice(productId, brandId, date);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getPriceList());
        assertEquals(new BigDecimal("25.45"), result.getPrice());
        assertEquals(1, result.getPriority());
        assertEquals("EUR", result.getCurrency());

        verify(repository).findByProductIdAndBrandId(productId, brandId, date);
    }

    @Test
    @DisplayName("Should throw NoApplicablePriceException when no price found")
    void shouldThrowExceptionWhenNoPriceFound() {
        // Given
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        when(repository.findByProductIdAndBrandId(productId, brandId, date))
                .thenReturn(Optional.empty());

        // When & Then
        NoApplicablePriceException exception = assertThrows(NoApplicablePriceException.class, () ->
                service.getApplicablePrice(productId, brandId, date));

        assertTrue(exception.getMessage().contains("No applicable price found"));
        assertTrue(exception.getMessage().contains("35455"));
        assertTrue(exception.getMessage().contains("1"));

        verify(repository).findByProductIdAndBrandId(productId, brandId, date);
    }

    @Test
    @DisplayName("Should handle repository exception gracefully")
    void shouldHandleRepositoryException() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        when(repository.findByProductIdAndBrandId(productId, brandId, date))
                .thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.getApplicablePrice(productId, brandId, date));

        assertEquals("Database connection error", exception.getMessage());
        verify(repository).findByProductIdAndBrandId(productId, brandId, date);
    }

    @Test
    @DisplayName("Should call repository with correct parameters")
    void shouldCallRepositoryWithCorrectParameters() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 30);
        Long brandId = 2L;
        Long productId = 12345L;

        Price price = Price.of(1L, brandId, date.minusHours(1), date.plusHours(1),
                1, productId, 0, new BigDecimal("100.00"), "USD");

        when(repository.findByProductIdAndBrandId(productId, brandId, date))
                .thenReturn(Optional.of(price));

        // When
        service.getApplicablePrice(productId, brandId, date);

        // Then
        verify(repository).findByProductIdAndBrandId(productId, brandId, date);
        verify(repository, times(1)).findByProductIdAndBrandId(any(), any(), any());
    }

    @Test
    @DisplayName("Should return exact price object from repository")
    void shouldReturnExactPriceObjectFromRepository() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price repositoryPrice = Price.of(99L, brandId,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                4, productId, 1, new BigDecimal("38.95"), "EUR");

        when(repository.findByProductIdAndBrandId(productId, brandId, date))
                .thenReturn(Optional.of(repositoryPrice));

        // When
        Price result = service.getApplicablePrice(productId, brandId, date);

        // Then
        assertSame(repositoryPrice, result);
        assertEquals(99L, result.getId());
        assertEquals(4, result.getPriceList());
        assertEquals(new BigDecimal("38.95"), result.getPrice());
    }
}