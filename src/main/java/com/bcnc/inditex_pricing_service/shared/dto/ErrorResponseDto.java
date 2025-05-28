package com.bcnc.inditex_pricing_service.shared.dto;

public class ErrorResponseDto {

    private final String code;
    private final String message;
    private final int status;

    public ErrorResponseDto(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

    public int getStatus() { return status; }
}
