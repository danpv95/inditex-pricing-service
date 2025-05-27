package com.bcnc.inditex_pricing_service.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponseDto {

    private Long productId;
    private Long brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private String currency;

    public PriceResponseDto() {
    }

    public PriceResponseDto(Long productId, Long brandId, Integer priceList,
                            LocalDateTime startDate, LocalDateTime endDate,
                            BigDecimal price, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.currency = currency;
    }

    public void setPrice(BigDecimal price) { this.price = price; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getProductId() { return productId; }
    public Long getBrandId() { return brandId; }
    public Integer getPriceList() { return priceList; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
}
