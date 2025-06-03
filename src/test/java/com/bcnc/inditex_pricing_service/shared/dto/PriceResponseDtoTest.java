package com.bcnc.inditex_pricing_service.shared.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceResponseDtoTest {

    @Test
    @DisplayName("Should create PriceResponseDto with default constructor")
    void shouldCreateWithDefaultConstructor() {
        PriceResponseDto dto = new PriceResponseDto();

        assertNotNull(dto);
        assertNull(dto.getProductId());
        assertNull(dto.getBrandId());
        assertNull(dto.getPriceList());
        assertNull(dto.getStartDate());
        assertNull(dto.getEndDate());
        assertNull(dto.getPrice());
        assertNull(dto.getCurrency());
    }

    @Test
    @DisplayName("Should create PriceResponseDto with all parameters")
    void shouldCreateWithAllParameters() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        Integer priceList = 2;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        BigDecimal price = new BigDecimal("25.45");
        String currency = "EUR";

        // When
        PriceResponseDto dto = new PriceResponseDto(productId, brandId, priceList,
                startDate, endDate, price, currency);

        // Then
        assertEquals(productId, dto.getProductId());
        assertEquals(brandId, dto.getBrandId());
        assertEquals(priceList, dto.getPriceList());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals(price, dto.getPrice());
        assertEquals(currency, dto.getCurrency());
    }

    @Test
    @DisplayName("Should set and get price correctly")
    void shouldSetAndGetPrice() {
        // Given
        PriceResponseDto dto = new PriceResponseDto();
        BigDecimal price = new BigDecimal("38.95");

        // When
        dto.setPrice(price);

        // Then
        assertEquals(price, dto.getPrice());
    }

    @Test
    @DisplayName("Should set and get brandId correctly")
    void shouldSetAndGetBrandId() {
        // Given
        PriceResponseDto dto = new PriceResponseDto();
        Long brandId = 2L;

        // When
        dto.setBrandId(brandId);

        // Then
        assertEquals(brandId, dto.getBrandId());
    }

    @Test
    @DisplayName("Should set and get productId correctly")
    void shouldSetAndGetProductId() {
        // Given
        PriceResponseDto dto = new PriceResponseDto();
        Long productId = 99999L;

        // When
        dto.setProductId(productId);

        // Then
        assertEquals(productId, dto.getProductId());
    }

    @Test
    @DisplayName("Should handle null values in setters")
    void shouldHandleNullValuesInSetters() {
        // Given
        PriceResponseDto dto = new PriceResponseDto();

        // When
        dto.setPrice(null);
        dto.setBrandId(null);
        dto.setProductId(null);

        // Then
        assertNull(dto.getPrice());
        assertNull(dto.getBrandId());
        assertNull(dto.getProductId());
    }
}