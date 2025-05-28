package com.bcnc.inditex_pricing_service.infrastructure.controller;

import com.bcnc.inditex_pricing_service.domain.model.Price;
import com.bcnc.inditex_pricing_service.domain.port.input.PriceUseCase;
import com.bcnc.inditex_pricing_service.shared.dto.PriceResponseDto;
import com.bcnc.inditex_pricing_service.shared.mapper.PriceResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceControllerTest {

    private PriceUseCase priceUseCase;
    private PriceResponseMapper responseMapper;
    private PriceController controller;

    @BeforeEach
    void setUp() {
        priceUseCase = mock(PriceUseCase.class);
        responseMapper = mock(PriceResponseMapper.class);
        controller = new PriceController(priceUseCase, responseMapper);
    }

    @Test
    void shouldReturnDtoFromUseCaseAndMapper() {
        // given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price domainPrice = Price.of(null, brandId, date.minusHours(1), date.plusHours(1),
                2, productId, 1, new BigDecimal("25.45"), "EUR");

        PriceResponseDto dto = new PriceResponseDto();
        dto.setProductId(productId);
        dto.setBrandId(brandId);
        dto.setPrice(new BigDecimal("25.45"));

        when(priceUseCase.getApplicablePrice(productId, brandId, date)).thenReturn(domainPrice);
        when(responseMapper.toDto(domainPrice)).thenReturn(dto);

        // when
        PriceResponseDto response = controller.getPrice(productId, brandId, date);

        // then
        assertNotNull(response);
        assertEquals(brandId, response.getBrandId());
        assertEquals(productId, response.getProductId());
        assertEquals(new BigDecimal("25.45"), response.getPrice());

        verify(priceUseCase).getApplicablePrice(productId, brandId, date);
        verify(responseMapper).toDto(domainPrice);
    }

    @Test
    void shouldPropagateExceptionWhenUseCaseFails() {
        Long productId = 99999L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 0, 0);

        when(priceUseCase.getApplicablePrice(productId, brandId, date))
                .thenThrow(new RuntimeException("No applicable price"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                controller.getPrice(productId, brandId, date));

        assertEquals("No applicable price", thrown.getMessage());
        verify(priceUseCase).getApplicablePrice(productId, brandId, date);
    }

    @Test
    void shouldHandleNullDtoFromMapper() {
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price price = Price.of(null, brandId, date.minusHours(1), date.plusHours(1),
                1, productId, 0, new BigDecimal("30.00"), "EUR");

        when(priceUseCase.getApplicablePrice(productId, brandId, date)).thenReturn(price);
        when(responseMapper.toDto(price)).thenReturn(null);

        PriceResponseDto response = controller.getPrice(productId, brandId, date);

        assertNull(response);
        verify(priceUseCase).getApplicablePrice(productId, brandId, date);
        verify(responseMapper).toDto(price);
    }

}
