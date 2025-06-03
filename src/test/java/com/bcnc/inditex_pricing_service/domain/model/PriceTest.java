package com.bcnc.inditex_pricing_service.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    @DisplayName("Should create Price with all parameters using of() method")
    void shouldCreatePriceWithAllParameters() {
        // Given
        Long id = 1L;
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        Integer priceList = 2;
        Long productId = 35455L;
        Integer priority = 1;
        BigDecimal price = new BigDecimal("25.45");
        String currency = "EUR";

        // When
        Price result = Price.of(id, brandId, startDate, endDate, priceList,
                productId, priority, price, currency);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(brandId, result.getBrandId());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        assertEquals(priceList, result.getPriceList());
        assertEquals(productId, result.getProductId());
        assertEquals(priority, result.getPriority());
        assertEquals(price, result.getPrice());
        assertEquals(currency, result.getCurrency());
    }

    @Test
    @DisplayName("Should create Price without ID using withoutId() method")
    void shouldCreatePriceWithoutId() {
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
        Price result = Price.withoutId(brandId, startDate, endDate, priceList,
                productId, priority, price, currency);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(brandId, result.getBrandId());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        assertEquals(priceList, result.getPriceList());
        assertEquals(productId, result.getProductId());
        assertEquals(priority, result.getPriority());
        assertEquals(price, result.getPrice());
        assertEquals(currency, result.getCurrency());
    }

    @Test
    @DisplayName("Should throw exception when start date is after end date")
    void shouldThrowExceptionWhenStartDateAfterEndDate() {
        // Given - Invalid dates: start > end
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Price.of(1L, 1L, startDate, endDate, 1, 35455L, 1,
                        new BigDecimal("25.45"), "EUR"));

        assertEquals("Start date cannot be after end date", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when start date equals end date but with time difference")
    void shouldThrowExceptionWhenStartDateAfterEndDateWithTime() {
        // Given - Same day but start time > end time
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 15, 0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Price.withoutId(1L, startDate, endDate, 2, 35455L, 1,
                        new BigDecimal("25.45"), "EUR"));

        assertEquals("Start date cannot be after end date", exception.getMessage());
    }

    @Test
    @DisplayName("Should allow start date equal to end date")
    void shouldAllowStartDateEqualToEndDate() {
        // Given - Same exact datetime
        LocalDateTime sameDateTime = LocalDateTime.of(2020, 6, 14, 15, 0);

        // When
        Price result = Price.of(1L, 1L, sameDateTime, sameDateTime, 1, 35455L, 1,
                new BigDecimal("25.45"), "EUR");

        // Then
        assertNotNull(result);
        assertEquals(sameDateTime, result.getStartDate());
        assertEquals(sameDateTime, result.getEndDate());
    }

    @Test
    @DisplayName("Should throw NullPointerException when required fields are null")
    void shouldThrowNullPointerExceptionForNullFields() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30);

        // Test null brandId
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, null, startDate, endDate, 1, 35455L, 1,
                        new BigDecimal("25.45"), "EUR"));

        // Test null startDate
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, 1L, null, endDate, 1, 35455L, 1,
                        new BigDecimal("25.45"), "EUR"));

        // Test null endDate
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, 1L, startDate, null, 1, 35455L, 1,
                        new BigDecimal("25.45"), "EUR"));

        // Test null priceList
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, 1L, startDate, endDate, null, 35455L, 1,
                        new BigDecimal("25.45"), "EUR"));

        // Test null productId
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, 1L, startDate, endDate, 1, null, 1,
                        new BigDecimal("25.45"), "EUR"));

        // Test null priority
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, 1L, startDate, endDate, 1, 35455L, null,
                        new BigDecimal("25.45"), "EUR"));

        // Test null price
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, 1L, startDate, endDate, 1, 35455L, 1, null, "EUR"));

        // Test null currency
        assertThrows(NullPointerException.class, () ->
                Price.of(1L, 1L, startDate, endDate, 1, 35455L, 1,
                        new BigDecimal("25.45"), null));
    }

    @Test
    @DisplayName("Should create valid Price with boundary dates")
    void shouldCreateValidPriceWithBoundaryDates() {
        // Given - Year boundary test
        LocalDateTime startDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        LocalDateTime endDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        // When
        Price result = Price.of(1L, 1L, startDate, endDate, 4, 35455L, 1,
                new BigDecimal("38.95"), "EUR");

        // Then
        assertNotNull(result);
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
    }
}