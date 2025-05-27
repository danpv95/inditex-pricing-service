package com.bcnc.inditex_pricing_service.domain.exception;

public class NoApplicablePriceException extends RuntimeException {
    public NoApplicablePriceException(Long productId, Long brandId) {
        super("No applicable price found for product " + productId + " and brand " + brandId);
    }
}
