package com.bcnc.inditex_pricing_service.infrastructure.exception;

import com.bcnc.inditex_pricing_service.domain.exception.NoApplicablePriceException;
import com.bcnc.inditex_pricing_service.shared.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    public static final String NO_APPLICABLE_PRICE = "NO_APPLICABLE_PRICE";

    @ExceptionHandler(NoApplicablePriceException.class)
    public ResponseEntity<ErrorResponseDto> handleNoApplicablePrice(NoApplicablePriceException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(
                        NO_APPLICABLE_PRICE,
                        ex.getMessage(),
                        status.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An unexpected error occurred";
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(
                        INTERNAL_ERROR,
                        message
                        , status.value()));
    }
}
