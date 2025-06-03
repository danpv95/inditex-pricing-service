package com.bcnc.inditex_pricing_service.infrastructure.controller;

import com.bcnc.inditex_pricing_service.domain.exception.NoApplicablePriceException;
import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.domain.port.input.PriceUseCase;
import com.bcnc.inditex_pricing_service.infrastructure.exception.GlobalExceptionHandler;
import com.bcnc.inditex_pricing_service.shared.mapper.PriceResponseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
@Import({GlobalExceptionHandler.class, PriceResponseMapper.class})
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PriceUseCase priceUseCase;

    private static final String BASE_URL = "/api/v1/prices";

    @Test
    @DisplayName("Should return price response when valid request")
    void shouldReturnPriceResponseWhenValidRequest() throws Exception {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price price = Price.of(1L, brandId,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2, productId, 1, new BigDecimal("25.45"), "EUR");

        when(priceUseCase.getApplicablePrice(productId, brandId, date))
                .thenReturn(price);

        // When & Then
        mockMvc.perform(get(BASE_URL)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-06-14T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    @DisplayName("Should return 404 when no applicable price found")
    void shouldReturn404WhenNoApplicablePriceFound() throws Exception {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 13, 10, 0);

        when(priceUseCase.getApplicablePrice(productId, brandId, date))
                .thenThrow(new NoApplicablePriceException(productId, brandId));

        // When & Then
        mockMvc.perform(get(BASE_URL)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-06-13T10:00:00"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("NO_APPLICABLE_PRICE"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("Should return 400 when missing required parameters")
    void shouldReturn400WhenMissingRequiredParameters() throws Exception {
        // When & Then - Missing productId
        mockMvc.perform(get(BASE_URL)
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isBadRequest());

        // When & Then - Missing brandId
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isBadRequest());

        // When & Then - Missing date
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when invalid date format")
    void shouldReturn400WhenInvalidDateFormat() throws Exception {
        // When & Then
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "invalid-date"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when invalid parameter types")
    void shouldReturn400WhenInvalidParameterTypes() throws Exception {
        // When & Then - Invalid productId
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "invalid")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isBadRequest());

        // When & Then - Invalid brandId
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "invalid")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 500 when unexpected error occurs")
    void shouldReturn500WhenUnexpectedErrorOccurs() throws Exception {
        // Given
        when(priceUseCase.getApplicablePrice(any(), any(), any()))
                .thenThrow(new RuntimeException("Database connection failed"));

        // When & Then
        mockMvc.perform(get(BASE_URL)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.status").value(500));
    }

    @Test
    @DisplayName("Should handle edge case with boundary dates")
    void shouldHandleEdgeCaseWithBoundaryDates() throws Exception {
        // Given - End of year boundary
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        Price price = Price.of(4L, brandId,
                LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                4, productId, 1, new BigDecimal("38.95"), "EUR");

        when(priceUseCase.getApplicablePrice(productId, brandId, date))
                .thenReturn(price);

        // When & Then
        mockMvc.perform(get(BASE_URL)
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("date", "2020-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }
}