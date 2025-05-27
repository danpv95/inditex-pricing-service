package com.bcnc.inditex_pricing_service.infrastructure.controller;

import com.bcnc.inditex_pricing_service.domain.port.input.PriceUseCase;
import com.bcnc.inditex_pricing_service.shared.dto.PriceResponseDto;
import com.bcnc.inditex_pricing_service.shared.mapper.PriceResponseMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {

    private final PriceUseCase priceUseCase;
    private final PriceResponseMapper responseMapper;

    public PriceController(PriceUseCase priceUseCase, PriceResponseMapper responseMapper) {
        this.priceUseCase = priceUseCase;
        this.responseMapper = responseMapper;
    }

    @GetMapping
    public PriceResponseDto getPrice(
            @RequestParam Long productId,
            @RequestParam Long brandId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        return responseMapper.toDto(
                priceUseCase.getApplicablePrice(productId, brandId, date)
        );
    }
}
