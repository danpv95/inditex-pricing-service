package com.bcnc.inditex_pricing_service.infrastructure.exception;

import com.bcnc.inditex_pricing_service.domain.exception.NoApplicablePriceException;
import com.bcnc.inditex_pricing_service.shared.dto.ErrorResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle NoApplicablePriceException and return 404 NOT_FOUND")
    void shouldHandleNoApplicablePriceException() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        NoApplicablePriceException exception = new NoApplicablePriceException(productId, brandId);

        // When
        ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleNoApplicablePrice(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponseDto errorDto = response.getBody();
        assertNotNull(errorDto);
        assertEquals("NO_APPLICABLE_PRICE", errorDto.getCode());
        assertEquals(404, errorDto.getStatus());
        assertTrue(errorDto.getMessage().contains("No applicable price found"));
        assertTrue(errorDto.getMessage().contains("35455"));
        assertTrue(errorDto.getMessage().contains("1"));
    }

    @Test
    @DisplayName("Should handle generic Exception and return 500 INTERNAL_SERVER_ERROR")
    void shouldHandleGenericException() {
        // Given
        Exception exception = new RuntimeException("Database connection failed");

        // When
        ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleGenericException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponseDto errorDto = response.getBody();
        assertNotNull(errorDto);
        assertEquals("INTERNAL_ERROR", errorDto.getCode());
        assertEquals(500, errorDto.getStatus());
        assertEquals("An unexpected error occurred", errorDto.getMessage());
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException as generic exception")
    void shouldHandleIllegalArgumentException() {
        // Given
        IllegalArgumentException exception = new IllegalArgumentException("Invalid parameter");

        // When
        ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleGenericException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponseDto errorDto = response.getBody();
        assertNotNull(errorDto);
        assertEquals("INTERNAL_ERROR", errorDto.getCode());
        assertEquals(500, errorDto.getStatus());
        assertEquals("An unexpected error occurred", errorDto.getMessage());
    }

    @Test
    @DisplayName("Should handle NullPointerException as generic exception")
    void shouldHandleNullPointerException() {
        // Given
        NullPointerException exception = new NullPointerException("Null value encountered");

        // When
        ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleGenericException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponseDto errorDto = response.getBody();
        assertNotNull(errorDto);
        assertEquals("INTERNAL_ERROR", errorDto.getCode());
        assertEquals(500, errorDto.getStatus());
        assertEquals("An unexpected error occurred", errorDto.getMessage());
    }

    @Test
    @DisplayName("Should verify constant values are correct")
    void shouldVerifyConstantValues() {
        // Then
        assertEquals("INTERNAL_ERROR", GlobalExceptionHandler.INTERNAL_ERROR);
        assertEquals("NO_APPLICABLE_PRICE", GlobalExceptionHandler.NO_APPLICABLE_PRICE);
    }

    @Test
    @DisplayName("Should handle NoApplicablePriceException with different product and brand IDs")
    void shouldHandleNoApplicablePriceExceptionWithDifferentIds() {
        // Given
        Long productId = 99999L;
        Long brandId = 5L;
        NoApplicablePriceException exception = new NoApplicablePriceException(productId, brandId);

        // When
        ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleNoApplicablePrice(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponseDto errorDto = response.getBody();
        assertNotNull(errorDto);
        assertEquals("NO_APPLICABLE_PRICE", errorDto.getCode());
        assertEquals(404, errorDto.getStatus());
        assertTrue(errorDto.getMessage().contains("99999"));
        assertTrue(errorDto.getMessage().contains("5"));
    }

    @Test
    @DisplayName("Should create proper error response structure")
    void shouldCreateProperErrorResponseStructure() {
        // Given
        Exception exception = new Exception("Test exception");

        // When
        ResponseEntity<ErrorResponseDto> response = exceptionHandler.handleGenericException(exception);

        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());

        ErrorResponseDto errorDto = response.getBody();

        // Verify all fields are present and not null/empty
        assertNotNull(errorDto.getCode());
        assertNotNull(errorDto.getMessage());

        // Verify field values are valid - FIX: No assertNotNull for primitive int
        assertFalse(errorDto.getCode().isEmpty());
        assertFalse(errorDto.getMessage().isEmpty());
        assertTrue(errorDto.getStatus() > 0); // Valid HTTP status code
    }
}