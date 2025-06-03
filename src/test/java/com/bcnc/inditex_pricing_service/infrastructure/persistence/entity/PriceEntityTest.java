package com.bcnc.inditex_pricing_service.infrastructure.persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityTest {

    @Test
    @DisplayName("Should create PriceEntity with all parameters")
    void shouldCreatePriceEntityWithAllParameters() {
        // Given
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        Integer priceList = 2;
        Long productId = 35455L;
        Integer priority = 1;
        BigDecimal price = new BigDecimal("25.45");
        String currency = "EUR";

        // When
        PriceEntity entity = new PriceEntity(brandId, startDate, endDate, priceList,
                productId, priority, price, currency);

        // Then
        assertNotNull(entity);
        assertNull(entity.getId()); // ID is auto-generated, should be null initially
        assertEquals(brandId, entity.getBrandId());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(priceList, entity.getPriceList());
        assertEquals(productId, entity.getProductId());
        assertEquals(priority, entity.getPriority());
        assertEquals(price, entity.getPrice());
        assertEquals(currency, entity.getCurrency());
    }

    @Test
    @DisplayName("Should create PriceEntity with protected constructor")
    void shouldCreatePriceEntityWithProtectedConstructor() {
        // When - This covers the protected no-args constructor used by JPA
        PriceEntity entity = new PriceEntity();

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getBrandId());
        assertNull(entity.getStartDate());
        assertNull(entity.getEndDate());
        assertNull(entity.getPriceList());
        assertNull(entity.getProductId());
        assertNull(entity.getPriority());
        assertNull(entity.getPrice());
        assertNull(entity.getCurrency());
    }

    @Test
    @DisplayName("Should handle all getters correctly")
    void shouldHandleAllGettersCorrectly() {
        // Given
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 15, 11, 0);
        Integer priceList = 3;
        Long productId = 35455L;
        Integer priority = 1;
        BigDecimal price = new BigDecimal("30.50");
        String currency = "EUR";

        PriceEntity entity = new PriceEntity(brandId, startDate, endDate, priceList,
                productId, priority, price, currency);

        // When & Then - Test all getters
        assertEquals(brandId, entity.getBrandId());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(priceList, entity.getPriceList());
        assertEquals(productId, entity.getProductId());
        assertEquals(priority, entity.getPriority());
        assertEquals(price, entity.getPrice());
        assertEquals(currency, entity.getCurrency());
    }

    @Test
    @DisplayName("Should handle different data types correctly")
    void shouldHandleDifferentDataTypesCorrectly() {
        // Given - Different data scenarios
        Long brandId = 2L;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 15, 16, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        Integer priceList = 4;
        Long productId = 99999L;
        Integer priority = 0; // Minimum priority
        BigDecimal price = new BigDecimal("999.99"); // High price
        String currency = "USD";

        // When
        PriceEntity entity = new PriceEntity(brandId, startDate, endDate, priceList,
                productId, priority, price, currency);

        // Then
        assertEquals(brandId, entity.getBrandId());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(priceList, entity.getPriceList());
        assertEquals(productId, entity.getProductId());
        assertEquals(priority, entity.getPriority());
        assertEquals(0, entity.getPriority().compareTo(0)); // Test Integer comparison
        assertEquals(price, entity.getPrice());
        assertTrue(entity.getPrice().compareTo(new BigDecimal("999.99")) == 0);
        assertEquals(currency, entity.getCurrency());
    }

    @Test
    @DisplayName("Should handle edge case values")
    void shouldHandleEdgeCaseValues() {
        // Given - Edge case data
        Long brandId = Long.MAX_VALUE;
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime endDate = LocalDateTime.MAX;
        Integer priceList = Integer.MAX_VALUE;
        Long productId = Long.MIN_VALUE;
        Integer priority = Integer.MIN_VALUE;
        BigDecimal price = new BigDecimal("0.01"); // Minimum price
        String currency = "A"; // Single character

        // When
        PriceEntity entity = new PriceEntity(brandId, startDate, endDate, priceList,
                productId, priority, price, currency);

        // Then
        assertEquals(brandId, entity.getBrandId());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(priceList, entity.getPriceList());
        assertEquals(productId, entity.getProductId());
        assertEquals(priority, entity.getPriority());
        assertEquals(price, entity.getPrice());
        assertEquals(currency, entity.getCurrency());
    }

    @Test
    @DisplayName("Should create multiple entities with different values")
    void shouldCreateMultipleEntitiesWithDifferentValues() {
        // Given
        PriceEntity entity1 = new PriceEntity(1L,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59),
                1, 35455L, 0, new BigDecimal("35.50"), "EUR");

        PriceEntity entity2 = new PriceEntity(1L,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2, 35455L, 1, new BigDecimal("25.45"), "EUR");

        // Then
        assertNotEquals(entity1.getPriceList(), entity2.getPriceList());
        assertNotEquals(entity1.getPriority(), entity2.getPriority());
        assertNotEquals(entity1.getPrice(), entity2.getPrice());
        assertNotEquals(entity1.getStartDate(), entity2.getStartDate());
        assertNotEquals(entity1.getEndDate(), entity2.getEndDate());
    }
}